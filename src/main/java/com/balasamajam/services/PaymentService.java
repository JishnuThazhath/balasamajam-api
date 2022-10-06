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
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

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

            if(memberOptional.isEmpty() || adminOptional.isEmpty()) {
                addPaymentResponseModel.setStatus("FAILED");
                addPaymentResponseModel.setMessage("No such member or invalid admin");
            }
            else
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
                transactionLogService.addNewTransactionLog(currentTime, member, addPaymentRequestModel.getAmount(), payment, null, TransactionType.PAYMENT, member.getTotal());
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

    public ResponseBaseModel<List<FetchPaymentResponseModel>> fetchPayment(FetchPaymentRequestModel fetchPaymentRequestModel)
    {
        ResponseBaseModel<List<FetchPaymentResponseModel>> listResponseBaseModel = new ResponseBaseModel<>();
        listResponseBaseModel.setStatus("OK");
        listResponseBaseModel.setMessage("Fetch payment details is successful");

        try
        {
            Date startDate = DateSupportUtil.getStartDate(fetchPaymentRequestModel.getDate());
            Date endDate = DateSupportUtil.getEndDate(fetchPaymentRequestModel.getDate());

            List<Payment> payments = paymentRepository.findByPaymentDateBetween(startDate, endDate);

            List<FetchPaymentResponseModel> fetchPaymentResponseModels = new ArrayList<>();

            for(Payment payment : payments)
            {
                FetchPaymentResponseModel fetchPaymentResponseModel = new FetchPaymentResponseModel();

                fetchPaymentResponseModel.setAmount(payment.getPaymentAmount());
                fetchPaymentResponseModel.setCollectedByFullName(payment.getCollectedBy().getFirstName() + " " + payment.getCollectedBy().getLastName());
                fetchPaymentResponseModel.setMemberFullName(payment.getCollectedFrom().getFullName());
                fetchPaymentResponseModel.setStartDate(startDate);
                fetchPaymentResponseModel.setEndDate(endDate);
                fetchPaymentResponseModel.setPaymentDate(payment.getPaymentDate());

                fetchPaymentResponseModels.add(fetchPaymentResponseModel);
            }

            listResponseBaseModel.setData(fetchPaymentResponseModels);
        }
        catch (Exception e)
        {
            listResponseBaseModel.setStatus("ERROR");
            listResponseBaseModel.setMessage("Error occurred while fetching payment info");
            e.printStackTrace();
        }

        return listResponseBaseModel;
    }
}
