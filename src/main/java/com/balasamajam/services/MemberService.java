package com.balasamajam.services;

import com.balasamajam.constants.ExpenseType;
import com.balasamajam.entities.Maranam;
import com.balasamajam.entities.OtherExpense;
import com.balasamajam.models.ExpenseRequestModel;
import com.balasamajam.models.ExpenseResponseModel;
import com.balasamajam.models.ResponseBaseModel;
import com.balasamajam.repositories.MaranamExpenseRepository;
import com.balasamajam.repositories.OtherExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MemberService
{
    @Autowired
    private OtherExpenseRepository otherExpenseRepository;

    @Autowired
    private MaranamExpenseRepository maranamExpenseRepository;

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

        otherExpenseRepository.save(otherExpense);
    }

    private void addMaranamExpense(ExpenseRequestModel expenseRequestModel)
    {
        Maranam maranam = new Maranam();
        maranam.setNote(expenseRequestModel.getNote());
        maranam.setDateOfDeath(new Timestamp(expenseRequestModel.getExpenseDate().getTime()));
        maranam.setName(expenseRequestModel.getExpenseDescription());
        maranam.setRelativeOf(null);
        maranam.setTotalCost(expenseRequestModel.getExpenseAmount());

        maranamExpenseRepository.save(maranam);
    }
}
