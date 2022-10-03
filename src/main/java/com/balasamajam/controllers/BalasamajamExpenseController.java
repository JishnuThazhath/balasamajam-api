package com.balasamajam.controllers;

import com.balasamajam.models.*;
import com.balasamajam.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalasamajamExpenseController
{
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/addExpense")
    public ResponseEntity<ResponseBaseModel<AddExpenseResponseModel>> addExpense(@RequestBody RequestBaseModel<AddExpenseRequestModel> addExpenseRequestModel)
    {
        return ResponseEntity.ok(expenseService.addExpense(addExpenseRequestModel.getData()));
    }

    @PostMapping("/fetchExpense")
    public ResponseEntity<ResponseBaseModel<List<FetchExpenseResponseModel>>> fetchExpense(@RequestBody RequestBaseModel<FetchExpenseRequestModel> fetchExpenseRequestModel)
    {
        return ResponseEntity.ok(expenseService.fetchExpenses(fetchExpenseRequestModel.getData()));
    }
}
