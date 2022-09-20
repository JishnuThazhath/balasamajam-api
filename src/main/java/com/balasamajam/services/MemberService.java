package com.balasamajam.services;

import com.balasamajam.constants.PaymentType;
import com.balasamajam.entities.Maranam;
import com.balasamajam.entities.Member;
import com.balasamajam.entities.MemberPayableSummary;
import com.balasamajam.entities.OtherExpense;
import com.balasamajam.models.*;
import com.balasamajam.repositories.MaranamExpenseRepository;
import com.balasamajam.repositories.MemberPayableSummaryRepository;
import com.balasamajam.repositories.MemberRepository;
import com.balasamajam.repositories.OtherExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberService
{
    @Autowired
    private OtherExpenseRepository otherExpenseRepository;

    @Autowired
    private MaranamExpenseRepository maranamExpenseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPayableSummaryRepository memberPayableSummaryRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public ResponseBaseModel<List<MemberPayableSearchResponse>> getMemberDebt(MemberPayableSearchRequestModel memberPayableSearchRequestModel)
    {
        ResponseBaseModel<List<MemberPayableSearchResponse>> memberPayableSearchResponseModel = new ResponseBaseModel<>();
        try
        {
            List<MemberPayableSearchResponse> memberPayableSearchResponseList = new ArrayList<>();
            List<Member> memberList = memberRepository.findByFirstName(memberPayableSearchRequestModel.getSearchKey());
            if(!memberList.isEmpty())
            {
                System.out.println("Total results fetched " + memberList.size());

                memberPayableSearchResponseModel.setStatus("OK");
                memberPayableSearchResponseModel.setMessage("Fetched payable details successfully");

                for (Member member : memberList) {
                    System.out.println("Member Name : " + member.getFirstName() + " " + member.getLastName());
                    List<MemberPayableSum> memberPayableSumList = memberPayableSummaryRepository.findMaranavariSum(member);

                    MemberPayableSearchResponse memberPayableSearchResponse = new MemberPayableSearchResponse();
                    memberPayableSearchResponse.setTotalPayableAmount((double) 0);
                    memberPayableSearchResponse.setTotalMaranavariAmount((double) 0);
                    memberPayableSearchResponse.setTotalMasavariAmount((double) 0);

                    memberPayableSearchResponse.setName(member.getFirstName() + " " + member.getLastName());
                    for (MemberPayableSum memberPayableSum : memberPayableSumList)
                    {
                        System.out.println("Payment Type : " + memberPayableSum.getPaymentType());
                        System.out.println("Sum : " + memberPayableSum.getSum());

                        if(memberPayableSum.getPaymentType() == PaymentType.MARANAVARI)
                        {
                            memberPayableSearchResponse.setTotalMaranavariAmount(memberPayableSum.getSum());
                        }
                        else if(memberPayableSum.getPaymentType() == PaymentType.MASAVARI)
                        {
                            memberPayableSearchResponse.setTotalMasavariAmount(memberPayableSum.getSum());
                        }
                        else if(memberPayableSum.getPaymentType() == PaymentType.OTHERS)
                        {
                            memberPayableSearchResponse.setTotalMasavariAmount(memberPayableSum.getSum());
                        }
                    }

                    memberPayableSearchResponse.setTotalPayableAmount(memberPayableSearchResponse.getTotalMaranavariAmount() + memberPayableSearchResponse.getTotalMasavariAmount());
                    memberPayableSearchResponseList.add(memberPayableSearchResponse);
                }
            }
            else
            {
                System.out.print("No such member");
                memberPayableSearchResponseModel.setStatus("FAILED");
                memberPayableSearchResponseModel.setMessage("No such member");
            }

            memberPayableSearchResponseModel.setData(memberPayableSearchResponseList);
        }
        catch (Exception e)
        {
            memberPayableSearchResponseModel.setStatus("ERROR");
            memberPayableSearchResponseModel.setMessage("Error happened");
            e.printStackTrace();
        }

        return memberPayableSearchResponseModel;
    }

    public ResponseBaseModel<MemberResponseModel> addMember(MemberRequestModel memberRequestModel) {
        ResponseBaseModel<MemberResponseModel> memberResponseModel = new ResponseBaseModel<>();
        try
        {
            Member member = new Member();
            member.setFirstName(memberRequestModel.getFirstName());
            member.setAddress(memberRequestModel.getAddress());
            member.setEmail(memberRequestModel.getEmail());
            member.setPhone(memberRequestModel.getPhone());
            member.setLastName(memberRequestModel.getLastName());

            memberRepository.save(member);

            memberResponseModel.setStatus("OK");
            memberResponseModel.setMessage("Added Member Successfully");
        }
        catch (DataIntegrityViolationException e)
        {
            e.printStackTrace();
            memberResponseModel.setStatus("FAILED");
            memberResponseModel.setMessage("Record already exists");
        }
        catch (Exception e)
        {
            memberResponseModel.setStatus("ERROR");
            memberResponseModel.setMessage("Added Member Failed");
            e.printStackTrace();
        }

        return memberResponseModel;
    }

    public ResponseBaseModel<ExpenseResponseModel> addExpense(ExpenseRequestModel expenseRequestModel)
    {
        ResponseBaseModel<ExpenseResponseModel> expenseRespose = new ResponseBaseModel<>();
        try
        {
            switch (expenseRequestModel.getExpenseType())
            {
                case OTHERS -> addOtherExpense(expenseRequestModel);
                case MARANAM -> addMaranamExpense(expenseRequestModel);
                default -> throw new Exception("Invalid Expense Type");
            }

            expenseRespose.setStatus("OK");
            expenseRespose.setMessage("Expense Added Successfully");
        }
        catch (DataIntegrityViolationException e)
        {
            e.printStackTrace();
            expenseRespose.setStatus("ERROR");
            expenseRespose.setMessage("Record already exists");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            expenseRespose.setStatus("ERROR");
            expenseRespose.setMessage("Adding expense failed for " + expenseRequestModel.getExpenseType());
        }
        return expenseRespose;
    }

    private void addOtherExpense(ExpenseRequestModel expenseRequestModel)
    {
        OtherExpense otherExpense = new OtherExpense();
        otherExpense.setDescription(expenseRequestModel.getExpenseDescription());
        otherExpense.setAmount(expenseRequestModel.getExpenseAmount());
        otherExpense.setDateOfExpense(new Timestamp(expenseRequestModel.getExpenseDate().getTime()));
        otherExpense.setNote(expenseRequestModel.getNote());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                otherExpenseRepository.save(otherExpense);
                splitTheAmountAmongAll(expenseRequestModel.getExpenseAmount(), PaymentType.OTHERS);
            }
        });
    }

    private void addMaranamExpense(ExpenseRequestModel expenseRequestModel)
    {
        Maranam maranam = new Maranam();
        maranam.setNote(expenseRequestModel.getNote());
        maranam.setDateOfDeath(new Timestamp(expenseRequestModel.getExpenseDate().getTime()));
        maranam.setName(expenseRequestModel.getExpenseDescription());
        maranam.setRelativeOf(null);
        maranam.setTotalCost(expenseRequestModel.getExpenseAmount());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                maranamExpenseRepository.save(maranam);
                splitTheAmountAmongAll(expenseRequestModel.getExpenseAmount(), PaymentType.MARANAVARI);
            }
        });
    }

    private void splitTheAmountAmongAll(Float amount, PaymentType paymentType)
    {
        List<Member> memberList = memberRepository.findAll();

        if(!memberList.isEmpty())
        {
            float eachPersonOws = amount/memberList.size();
            for (Member member : memberList)
            {
                MemberPayableSummary memberPayableSummary = new MemberPayableSummary();
                memberPayableSummary.setMember(member);
                memberPayableSummary.setAmount(eachPersonOws);
                memberPayableSummary.setPaymentType(paymentType);
                memberPayableSummary.setPaymentAddedOn(new Timestamp(Instant.now().toEpochMilli()));

                memberPayableSummaryRepository.save(memberPayableSummary);
            }
        }
    }
}
