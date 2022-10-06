package com.balasamajam.services;

import com.balasamajam.constants.ExpenseType;
import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Expense;
import com.balasamajam.entities.Member;
import com.balasamajam.models.*;
import com.balasamajam.repositories.AdminRepository;
import com.balasamajam.repositories.ExpenseRepository;
import com.balasamajam.repositories.MemberRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TransactionLogService transactionLogService;

    @Value("${default.maranavari}")
    private double defaultMaranavari;

    @Transactional
    public ResponseBaseModel<AddExpenseResponseModel> addExpense(AddExpenseRequestModel addExpenseRequestModel) {
        ResponseBaseModel<AddExpenseResponseModel> addExpenseResponseModel = new ResponseBaseModel<>();
        addExpenseResponseModel.setStatus("OK");
        addExpenseResponseModel.setMessage("Successfully added expense");

        try {
            String memberIdString = addExpenseRequestModel.getMemberId();
            String adminByIdString = addExpenseRequestModel.getAddedByAdminId();
            Timestamp dateOfDeath = new Timestamp(addExpenseRequestModel.getDateOfDeath().getTime());

            Member member = null;
            Optional<Member> memberOptional;
            if (memberIdString != null && !memberIdString.isEmpty()) {
                memberOptional = memberRepository.findById(UUID.fromString(memberIdString));
                if(!memberOptional.isEmpty())
                {
                    member = memberOptional.get();
                }
            }

            if (adminByIdString.isEmpty()) {
                throw new InvalidRequestStateException("Invalid Admin");
            }


            Optional<Admin> adminOptional = adminRepository.findById(UUID.fromString(adminByIdString));

            if (adminOptional.isEmpty()) {
                addExpenseResponseModel.setStatus("FAILED");
                addExpenseResponseModel.setMessage("No such member or invalid admin");
            } else {
                Expense expense = new Expense();

                expense.setAmount(addExpenseRequestModel.getAmount());
                expense.setDescription(addExpenseRequestModel.getDescription());
                expense.setExpenseType(addExpenseRequestModel.getExpenseType());
                expense.setNotes(addExpenseRequestModel.getNotes());
                expense.setRelationToMember(member);
                expense.setAddedBy(adminOptional.get());
                expense.setDate(dateOfDeath);

                expenseRepository.save(expense);

                // Only MARANAM expense type add 100 rs to all the members and logs in the transaction table.
                if (addExpenseRequestModel.getExpenseType().equals(ExpenseType.MARANAM)) {
                    List<Member> allMembers = memberRepository.findAll();

                    for (Member eachMem : allMembers) {

                        double currentMaranavari = eachMem.getMaranavari();
                        double currentTotal = eachMem.getTotal();

                        eachMem.setMaranavari(currentMaranavari + defaultMaranavari);
                        eachMem.setTotal(currentTotal + defaultMaranavari);
                        memberRepository.save(eachMem);

                        transactionLogService.addNewTransactionLog(new Timestamp(System.currentTimeMillis()), eachMem,
                                addExpenseRequestModel.getAmount(), null, expense, TransactionType.MARANAVARI, eachMem.getTotal());
                    }

                } else if(addExpenseRequestModel.getExpenseType().equals(ExpenseType.OTHERS)) {
                    transactionLogService.addNewTransactionLog(new Timestamp(System.currentTimeMillis()), null,
                            addExpenseRequestModel.getAmount(), null, expense, TransactionType.OTHERS, 0);
                }
            }
        } catch (Exception e) {
            addExpenseResponseModel.setStatus("ERROR");
            addExpenseResponseModel.setMessage("Error while adding expense");
            e.printStackTrace();
        }

        return addExpenseResponseModel;
    }

    // Implement infinite scroll technology here.
    public ResponseBaseModel<List<FetchExpenseResponseModel>> fetchExpenses(FetchExpenseRequestModel fetchExpenseRequestModel) {

        ResponseBaseModel<List<FetchExpenseResponseModel>> listResponseBaseModel = new ResponseBaseModel<>();
        listResponseBaseModel.setStatus("OK");
        listResponseBaseModel.setMessage("Successfully fetched expense info");

        try
        {
            List<FetchExpenseResponseModel> fetchExpenseResponseModelList = new ArrayList<>();

            List<Expense> expenses;
            String expenseType = fetchExpenseRequestModel.getExpenseType();

            if(expenseType.equals(ExpenseType.MARANAM.toString())
                    || expenseType.equals(ExpenseType.OTHERS.toString()))
            {
                expenses = expenseRepository.findByExpenseType(ExpenseType.valueOf(expenseType));
            }
            else
            {
                expenses = expenseRepository.findAll();
            }

            for(Expense expense : expenses)
            {
                FetchExpenseResponseModel fetchExpenseResponseModel = new FetchExpenseResponseModel();
                fetchExpenseResponseModel.setAmount(expense.getAmount());
                fetchExpenseResponseModel.setAddedBy(expense.getAddedBy().getFirstName() + " " + expense.getAddedBy().getLastName());
                fetchExpenseResponseModel.setDescription(expense.getDescription());
                fetchExpenseResponseModel.setExpenseType(expense.getExpenseType());
                fetchExpenseResponseModel.setRelatedTo(expense.getRelationToMember().getFullName());
                fetchExpenseResponseModel.setDate(expense.getDate());

                fetchExpenseResponseModelList.add(fetchExpenseResponseModel);
            }

            listResponseBaseModel.setData(fetchExpenseResponseModelList);
        }
        catch (Exception e)
        {
            listResponseBaseModel.setStatus("ERROR");
            listResponseBaseModel.setStatus("Error occurred while fetching expense info");
            e.printStackTrace();
        }

        return listResponseBaseModel;
    }
}
