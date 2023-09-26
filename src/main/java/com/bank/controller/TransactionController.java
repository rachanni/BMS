package com.bank.controller;

import com.bank.entity.Transaction;
import com.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/perform-transaction")
    public String performTransaction(@RequestBody Transaction transaction) {
        System.out.println(transaction);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        transaction.setTransactionDate(new Date());
//        transaction.setAccount();
        return transactionService.performTransaction(name,transaction);
    }
}
