package com.balasamajam.controllers;

import com.balasamajam.models.*;
import com.balasamajam.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalasamajamPaymentController
{
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addPayment")
    public ResponseEntity<ResponseBaseModel<AddPaymentResponseModel>> addNewPayment(@RequestBody RequestBaseModel<AddPaymentRequestModel> addPaymentRequestModel)
    {
        return ResponseEntity.ok(paymentService.addNewPayment(addPaymentRequestModel.getData()));
    }

    @PostMapping("/fetchPayments")
    public ResponseEntity<ResponseBaseModel<FetchPaymentResponseModel>> fetchPayment(@RequestBody RequestBaseModel<FetchPaymentRequestModel> fetchPaymentRequestModel)
    {
        System.out.println(fetchPaymentRequestModel.getData().getDate());
        return ResponseEntity.ok(paymentService.fetchPayments(fetchPaymentRequestModel.getData()));
    }
}
