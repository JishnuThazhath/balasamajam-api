package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Member;
import com.balasamajam.entities.Payment;
import com.balasamajam.models.*;
import com.balasamajam.repositories.AdminRepository;
import com.balasamajam.repositories.MemberRepository;
import com.balasamajam.repositories.PaymentRepository;
import com.balasamajam.utils.DateSupportUtil;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.balasamajam.specifications.PaymentSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class PaymentService
{
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TransactionLogService transactionLogService;

    @Transactional
    public ResponseBaseModel<AddPaymentResponseModel> addNewPayment(AddPaymentRequestModel addPaymentRequestModel)
    {
        ResponseBaseModel<AddPaymentResponseModel> addPaymentResponseModel = new ResponseBaseModel<>();
        addPaymentResponseModel.setStatus("OK");
        addPaymentResponseModel.setMessage("Successfully added payment");

        try
        {
            String memberIdString = addPaymentRequestModel.getMemberId();
            String adminByIdString = addPaymentRequestModel.getCollectedById();

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            if(addPaymentRequestModel.getAmount() <= 0)
            {
                throw new InvalidRequestStateException("Invalid Amount");
            }

            if(memberIdString.isEmpty()) {
                throw new InvalidRequestStateException("Invalid Member");
            }

            if(adminByIdString.isEmpty()) {
                throw new InvalidRequestStateException("Invalid Admin");
            }

            Optional<Member> memberOptional = memberRepository.findById(UUID.fromString(memberIdString));
            Optional<Admin> adminOptional = adminRepository.findById(UUID.fromString(adminByIdString));

            if(memberOptional.isPresent() && adminOptional.isPresent())
            {
                Member member = memberOptional.get();
                Admin admin = adminOptional.get();

                Payment payment = new Payment();
                payment.setPaymentDate(currentTime);
                payment.setPaymentAmount(addPaymentRequestModel.getAmount());
                payment.setCollectedBy(admin);
                payment.setCollectedFrom(member);
                payment.setComments(addPaymentRequestModel.getComments());

                paymentRepository.save(payment);

                // Update the payment in member table.
                double payedAmount = addPaymentRequestModel.getAmount();
                double currentMaranavari = member.getMaranavari();
                double currentTotal = member.getTotal();

                /*
                 * The payment amount must be deducted from pending maranavari.
                 * */

                // Maranavari
                if(payedAmount <= currentMaranavari)
                {
                    member.setMaranavari(currentMaranavari - payedAmount);
                    payedAmount = 0.0;
                }
                else
                {
                    payedAmount = payedAmount - currentMaranavari;
                    member.setMaranavari(0.0);
                }

                // Masavari
                double currentMasavari = member.getMasavari();
                if(payedAmount <= currentMasavari)
                {
                    member.setMasavari(currentMasavari - payedAmount);
                }
                else
                {
                    member.setMasavari(0.0);
                }

                member.setTotal(currentTotal - addPaymentRequestModel.getAmount());

                memberRepository.save(member);
                transactionLogService.addNewTransactionLog(member, addPaymentRequestModel.getAmount(), payment, null, TransactionType.PAYMENT, member.getTotal());
            }
            else
            {
                addPaymentResponseModel.setStatus("FAILED");
                addPaymentResponseModel.setMessage("No such member or invalid admin");
            }
        }
        catch (InvalidRequestStateException e) {
            addPaymentResponseModel.setStatus("BAD_REQUEST");
            addPaymentResponseModel.setMessage("Validation failed " + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            addPaymentResponseModel.setStatus("ERROR");
            addPaymentResponseModel.setMessage("Request failed with exception" + e.getMessage());
            e.printStackTrace();
        }

        return addPaymentResponseModel;
    }

    public ResponseBaseModel<List<PaymentModel>> fetchPayment(FetchPaymentRequestModel fetchPaymentRequestModel)
    {
        ResponseBaseModel<List<PaymentModel>> listResponseBaseModel = new ResponseBaseModel<>();
        listResponseBaseModel.setStatus("OK");
        listResponseBaseModel.setMessage("Fetch payment details is successful");

        try
        {
            Date startDate = DateSupportUtil.getStartDate(fetchPaymentRequestModel.getDate());
            Date endDate = DateSupportUtil.getEndDate(fetchPaymentRequestModel.getDate());

            List<Payment> payments = paymentRepository.findByPaymentDateBetween(startDate, endDate);

            List<PaymentModel> paymentModels = new ArrayList<>();

            for(Payment payment : payments)
            {
                PaymentModel paymentModel = new PaymentModel();

                paymentModel.setAmount(payment.getPaymentAmount());
                paymentModel.setCollectedByFullName(payment.getCollectedBy().getFirstName() + " " + payment.getCollectedBy().getLastName());
                paymentModel.setMemberFullName(payment.getCollectedFrom().getFullName());
                paymentModel.setPaymentDate(payment.getPaymentDate());

                paymentModels.add(paymentModel);
            }

            listResponseBaseModel.setData(paymentModels);
        }
        catch (Exception e)
        {
            listResponseBaseModel.setStatus("ERROR");
            listResponseBaseModel.setMessage("Error occurred while fetching payment info");
            e.printStackTrace();
        }

        return listResponseBaseModel;
    }

    public ResponseBaseModel<FetchPaymentResponseModel> fetchPayments(FetchPaymentRequestModel request)
    {
        ResponseBaseModel<FetchPaymentResponseModel> response = new ResponseBaseModel<>();
        response.setStatus("OK");
        response.setMessage("Successfully fetched collection summary info.");

        try
        {
            Date startDate = DateSupportUtil.getStartDate(request.getDate());
            Date endDate = DateSupportUtil.getEndDate(request.getDate());

            System.out.println(" START DATE > " + startDate);
            System.out.println(" END DATE > " + endDate);

            Specification<Payment> paymentSpecification = where(paymentStartDate(startDate)).and(paymentEndDate(endDate));

            String adminId = request.getCollectedById();
            System.out.println("Admin Id " + adminId);
            if(adminId != null && !adminId.isEmpty())
            {
                Optional<Admin> adminOptional = adminRepository.findById(UUID.fromString(adminId));
                if(adminOptional.isPresent())
                {
                    Admin admin = adminOptional.get();
                    System.out.println("Adding admin specification for admin " + admin.getFirstName());
                    paymentSpecification = paymentSpecification.and(paymentCollectedBy(admin));

                }
            }


            String memberId = request.getMemberId();
            System.out.println("Member Id " + memberId);
            if(memberId != null && !memberId.isEmpty())
            {
                Optional<Member> memberOptional = memberRepository.findById(UUID.fromString(memberId));
                if(memberOptional.isPresent())
                {
                    Member member = memberOptional.get();
                    System.out.println("Adding member specification for " + member.getFullName());
                    paymentSpecification = paymentSpecification.and(paymentCollectedFrom(member));

                }
            }

            FetchPaymentResponseModel fetchPaymentResponseModel = new FetchPaymentResponseModel();

            List<Payment> payments = paymentRepository.findAll(paymentSpecification);
            List<PaymentModel> payementsList = generatePaymentsResponseObject(payments, startDate, endDate);

            fetchPaymentResponseModel.setPayments(payementsList);
            fetchPaymentResponseModel.setStartDate(startDate);
            fetchPaymentResponseModel.setEndDate(endDate);

            response.setData(fetchPaymentResponseModel);
        }
        catch (Exception e)
        {
            response.setStatus("ERROR");
            response.setMessage("An error occurred while fetching collection summary info.");
            e.printStackTrace();
        }

        return response;
    }

    private List<PaymentModel> generatePaymentsResponseObject(List<Payment> payments, Date startDate, Date endDate)
    {
        List<PaymentModel> paymentModels = new ArrayList<>();

        for(Payment payment : payments)
        {
            PaymentModel paymentModel = new PaymentModel();

            Admin collectedBy = payment.getCollectedBy();
            paymentModel.setCollectedByFullName(collectedBy.getFirstName() + " " + collectedBy.getLastName());

            Member collectedFrom = payment.getCollectedFrom();
            paymentModel.setMemberFullName(collectedFrom.getFullName());

            paymentModel.setPaymentDate(payment.getPaymentDate());
            paymentModel.setAmount(payment.getPaymentAmount());

            paymentModels.add(paymentModel);
        }

        return paymentModels;
    }

    @Transactional
    public void addPayment(double amount, Member member, Date date)
    {
        Payment payment = new Payment();
        payment.setPaymentAmount(amount);
        payment.setCollectedFrom(member);
        payment.setPaymentDate(Timestamp.from(date.toInstant()));
        paymentRepository.save(payment);
        transactionLogService.addNewTransactionLog(member, amount, payment, null, TransactionType.PAYMENT, member.getTotal());
    }
}
