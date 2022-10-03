package com.balasamajam.services;

import com.balasamajam.entities.Member;
import com.balasamajam.models.MemberRequestModel;
import com.balasamajam.models.MemberResponseModel;
import com.balasamajam.models.ResponseBaseModel;
import com.balasamajam.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class MemberService
{
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;


    public ResponseBaseModel<MemberResponseModel> addMember(MemberRequestModel memberRequestModel) {
        ResponseBaseModel<MemberResponseModel> memberResponseModel = new ResponseBaseModel<>();
        try
        {
            Member member = new Member();
            member.setFullName(memberRequestModel.getFullName());
            member.setLocalizedFullName(memberRequestModel.getLocalizedFullName());
            member.setAddress(memberRequestModel.getAddress());
            member.setEmail(memberRequestModel.getEmail());
            member.setPhone(memberRequestModel.getPhone());

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
}
