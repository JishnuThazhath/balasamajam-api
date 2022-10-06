package com.balasamajam.services;

import com.balasamajam.entities.Member;
import com.balasamajam.models.*;
import com.balasamajam.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService
{
    @Autowired
    private MemberRepository memberRepository;


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

    public ResponseBaseModel<List<SearchMemberResponseModel>> fetchMembers(SearchMemberRequestModel searchMemberRequestModel)
    {
        ResponseBaseModel<List<SearchMemberResponseModel>> searchMemberResponseModels = new ResponseBaseModel<>();
        searchMemberResponseModels.setStatus("OK");
        searchMemberResponseModels.setMessage("Successfully fetched member details");

        try
        {
            List<SearchMemberResponseModel> searchMemberResponseModelList = new ArrayList<>();

            List<Member> members = memberRepository.findMembers(searchMemberRequestModel.getSearchText().toLowerCase());

            for(Member member : members)
            {
                SearchMemberResponseModel searchMemberResponseModel = new SearchMemberResponseModel();
                searchMemberResponseModel.setMemberFullName(member.getFullName());
                searchMemberResponseModel.setMemberLocalName(member.getLocalizedFullName());
                searchMemberResponseModel.setMaranavari(member.getMaranavari());
                searchMemberResponseModel.setMasavari(member.getMasavari());
                searchMemberResponseModel.setTotal(member.getTotal());
                searchMemberResponseModel.setPhoneNumber(member.getPhone());

                searchMemberResponseModelList.add(searchMemberResponseModel);
            }

            searchMemberResponseModels.setData(searchMemberResponseModelList);
        }
        catch (Exception e)
        {
            searchMemberResponseModels.setStatus("ERROR");
            searchMemberResponseModels.setMessage("Error while fetching member details");
            e.printStackTrace();
        }

        return searchMemberResponseModels;
    }

    public ResponseBaseModel<List<MemberBasicModel>> getAllMembers() {
        ResponseBaseModel<List<MemberBasicModel>> memberBasicResponseModel = new ResponseBaseModel<>();
        memberBasicResponseModel.setStatus("OK");
        memberBasicResponseModel.setMessage("Succefully fetched all member basic details");

        try {
            List<MemberBasicModel> memberBasicModelList = new ArrayList<>();
            List<Member> members = memberRepository.findAll();
            for(Member member : members) {
                MemberBasicModel memberBasicModel = new MemberBasicModel();
                memberBasicModel.setMemberId(member.getUuid());
                memberBasicModel.setMemberFullName(member.getFullName());

                memberBasicModelList.add(memberBasicModel);
            }

            memberBasicResponseModel.setData(memberBasicModelList);
        }
        catch (Exception e)
        {
            memberBasicResponseModel.setStatus("ERROR");
            memberBasicResponseModel.setMessage("Error occurred while fetching member basic info");
            e.printStackTrace();
        }

        return  memberBasicResponseModel;
    }
}
