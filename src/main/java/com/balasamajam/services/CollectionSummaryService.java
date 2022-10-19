package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Member;
import com.balasamajam.entities.Payment;
import com.balasamajam.models.CollectionSummaryRequestModel;
import com.balasamajam.models.CollectionSummaryResposneModel;
import com.balasamajam.models.ResponseBaseModel;
import com.balasamajam.repositories.AdminRepository;
import com.balasamajam.repositories.PaymentRepository;
import com.balasamajam.utils.DateSupportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.balasamajam.specifications.PaymentSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CollectionSummaryService
{
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AdminRepository adminRepository;

    public ResponseBaseModel<List<CollectionSummaryResposneModel>> fetchCollectionSummary(CollectionSummaryRequestModel request)
    {
        ResponseBaseModel<List<CollectionSummaryResposneModel>> resposne = new ResponseBaseModel<>();
        resposne.setStatus("OK");
        resposne.setMessage("Successfully fetched collection summary info.");

        try
        {
            String adminId = request.getAdminId();
            List<Payment> payments;

            Date startDate = DateSupportUtil.getStartDate(request.getDate());
            Date endDate = DateSupportUtil.getEndDate(request.getDate());

            System.out.println(" START DATE > " + startDate);
            System.out.println(" END DATE > " + endDate);

            if(adminId != null && !adminId.isEmpty())
            {
                Optional<Admin> adminOptional = adminRepository.findById(UUID.fromString(adminId));
                if(adminOptional.isPresent())
                {
                    Admin admin = adminOptional.get();
                    payments = paymentRepository.findAll(where(paymentCollectedBy(admin)).and(paymentStartDate(startDate)).and(paymentEndDate(endDate)));

                    if(payments.isEmpty())
                    {
                        resposne.setStatus("OK");
                        resposne.setMessage("No results found.");
                    }

                    List<CollectionSummaryResposneModel> collectionSummaryResposneModel = generateCollectionSummaryResponse(payments, startDate, endDate);
                    resposne.setData(collectionSummaryResposneModel);
                }
                else
                {
                    resposne.setStatus("OK");
                    resposne.setMessage("No such admin.");
                }
            }
            else
            {
                payments = paymentRepository.findAll(where(paymentStartDate(startDate)).and(paymentEndDate(endDate)));

                if(payments.isEmpty())
                {
                    resposne.setStatus("OK");
                    resposne.setMessage("No results found.");
                }

                List<CollectionSummaryResposneModel> collectionSummaryResposneModel = generateCollectionSummaryResponse(payments, startDate, endDate);
                resposne.setData(collectionSummaryResposneModel);
            }
        }
        catch (Exception e)
        {
            resposne.setStatus("ERROR");
            resposne.setMessage("An error occurred while fetching collection summary info.");
            e.printStackTrace();
        }

        return resposne;
    }

    private List<CollectionSummaryResposneModel> generateCollectionSummaryResponse(List<Payment> payments, Date startDate, Date endDate)
    {
        List<CollectionSummaryResposneModel> collectionSummaryResposneModelList = new ArrayList<>();

        for(Payment payment : payments)
        {
            CollectionSummaryResposneModel collectionSummaryResposneModel = new CollectionSummaryResposneModel();

            Admin collectedBy = payment.getCollectedBy();
            collectionSummaryResposneModel.setCollectedByFullName(collectedBy.getFirstName() + " " + collectedBy.getLastName());

            Member collectedFrom = payment.getCollectedFrom();
            collectionSummaryResposneModel.setCollectedFromFullName(collectedFrom.getFullName());

            collectionSummaryResposneModel.setPaymentDate(payment.getPaymentDate());
            collectionSummaryResposneModel.setStartDate(startDate);
            collectionSummaryResposneModel.setEndDate(endDate);
            collectionSummaryResposneModel.setPaidAmount(payment.getPaymentAmount());

            collectionSummaryResposneModelList.add(collectionSummaryResposneModel);
        }

        return collectionSummaryResposneModelList;
    }
}
