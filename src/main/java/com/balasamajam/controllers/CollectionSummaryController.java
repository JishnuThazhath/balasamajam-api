package com.balasamajam.controllers;

import com.balasamajam.models.CollectionSummaryRequestModel;
import com.balasamajam.models.CollectionSummaryResposneModel;
import com.balasamajam.models.RequestBaseModel;
import com.balasamajam.models.ResponseBaseModel;
import com.balasamajam.services.CollectionSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectionSummaryController
{

    @Autowired
    private CollectionSummaryService collectionSummaryService;

    @PostMapping("/fetchCollectionSummary")
    public ResponseEntity<ResponseBaseModel<List<CollectionSummaryResposneModel>>> fetchCollectionSummary(@RequestBody RequestBaseModel<CollectionSummaryRequestModel> collectionSummaryRequest)
    {
        return ResponseEntity.ok(collectionSummaryService.fetchCollectionSummary(collectionSummaryRequest.getData()));
    }
}
