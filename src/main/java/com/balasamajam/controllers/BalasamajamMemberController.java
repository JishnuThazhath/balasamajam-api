package com.balasamajam.controllers;

import com.balasamajam.models.*;
import com.balasamajam.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalasamajamMemberController
{
    @Autowired
    private MemberService memberService;

    @PostMapping("/fetchMembers")
    public ResponseEntity<ResponseBaseModel<List<SearchMemberResponseModel>>> fetchMembers(@RequestBody RequestBaseModel<SearchMemberRequestModel> searchMemberRequestModel)
    {
        System.out.println("search String " + searchMemberRequestModel.getData().getSearchText());
        return ResponseEntity.ok(memberService.fetchMembers(searchMemberRequestModel.getData()));
    }

    @PostMapping("/addMember")
    public ResponseEntity<ResponseBaseModel<MemberResponseModel>> addMember(@RequestBody RequestBaseModel<MemberRequestModel> memberRequestModel)
    {
        System.out.print("Add member request received " + memberRequestModel.getData().getFullName());
        return ResponseEntity.status(HttpStatus.OK).body(memberService.addMember(memberRequestModel.getData()));
    }

    // pass token as query param to secure the communication
    @GetMapping("/getAllMembers")
    public ResponseEntity<ResponseBaseModel<List<MemberBasicModel>>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
}
