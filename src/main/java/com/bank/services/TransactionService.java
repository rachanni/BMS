package com.bank.services;

import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.entity.UserInfo;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

//    double amount, long accountNumber
    public String performTransaction(String name, Transaction transaction) {
        transaction.setTransactionDate(new Date());
        UserInfo user =userInfoRepository.findByUserName(name).get();
        Account account = user.getAccount();
//        Account account = accountRepository.findById(transaction.getAccount().getAccountNumber()).get();
//        account.setUser(userInfoRepository.findByUserName(name).get());
        double balance = account.getBalance();

        if (transaction.getTransactionType().equals("credit")) {
            balance += transaction.getAmount();
            account.setBalance(balance);
            transaction.setAccount(account);
            transactionRepository.save(transaction);
            return "Account credited successfully, Updated balance is "+balance+"Rs";
        }
        else if (transaction.getTransactionType().equals("withdraw")) {
            if (balance < transaction.getAmount()) {
                return "Failed to withdraw money, Insufficient balance \nBalance : "+balance+"Rs";
            }
            else{
                balance -= transaction.getAmount();
                account.setBalance(balance);
                transaction.setAccount(account);
                transactionRepository.save(transaction);
                return "Account debited with "+transaction.getAmount()+"Rs"+", Updated balance is "+balance+"Rs";
            }
        }
        else {
            return "Failed to process transaction,Please enter correct details for transaction";
        }
    }
}
