package com.balasamajam.controllers;

import com.balasamajam.models.*;
import com.balasamajam.services.MemberService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalasamajamMemberController
{
    @Autowired
    private MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<String> fetchMember(@RequestBody RequestBaseModel<SearchMemberRequestModel> searchMemberRequestModel)
    {
        System.out.print("print " + searchMemberRequestModel.getData().getSearchText());
        return ResponseEntity.ok("done");
    }

    @PostMapping("/expense/add")
    public ResponseEntity<ResponseBaseModel<ExpenseResponseModel>> addExpense(@RequestBody RequestBaseModel<ExpenseRequestModel> expenseRequestModel)
    {
        System.out.println("Request received to add an expense");
        return ResponseEntity.status(HttpStatus.OK).body(memberService.addExpense(expenseRequestModel.getData()));
    }

    @PostMapping("/member/add")
    public ResponseEntity<ResponseBaseModel<MemberResponseModel>> addMember(@RequestBody RequestBaseModel<MemberRequestModel> memberRequestModel)
    {
        System.out.print("Add member request received");
        return ResponseEntity.status(HttpStatus.OK).body(memberService.addMember(memberRequestModel.getData()));
    }

    @PostMapping("/member/payable")
    public ResponseEntity<ResponseBaseModel<List<MemberPayableSearchResponse>>> fetchMemberPayableSummary(@RequestBody RequestBaseModel<MemberPayableSearchRequestModel> memberPayableSearchRequestModel)
    {
        System.out.println("Fetch member payable " + memberPayableSearchRequestModel.getData().getSearchKey());
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberDebt(memberPayableSearchRequestModel.getData()));
    }
}
