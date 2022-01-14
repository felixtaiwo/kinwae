package com.kinwae.challenge.codingChallenge.Transaction;

import com.kinwae.challenge.codingChallenge.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public void addTransactions(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }
    public Object getTransactions(int page,int userId,String text,String date){
        Pageable paging = PageRequest.of(page, 10, Sort.Direction.DESC, "date");
        boolean b = !Objects.equals(text, "") || !Objects.equals(date, "");
        if(userId==0){
            if(b){
                return filterOutAmout(transactionRepository.findAll(text,date,paging));
            }
            return filterOutAmout(transactionRepository.findAll(paging));
        }
        else {
            if(b){
                return transactionRepository.findByUser(new User(userId),text,date,paging);
            }
           return (transactionRepository.findByUser(new User(userId),paging));
        }

    }
    private Object filterOutAmout(Object transactionPage){
        Page page = (Page) transactionPage;
        List<Transaction> transactions =(List<Transaction>) page.getContent();
        List<Object> filteredTransactions = new ArrayList<>();
        Map<String,Object> filteredTransaction = new HashMap<>();
        for (Transaction t:transactions){
            filteredTransaction.put("id",t.getId());
            filteredTransaction.put("transactionType",t.getTransactionType());
            filteredTransaction.put("Date",t.getDate());
            filteredTransactions.add(filteredTransaction);
            filteredTransaction = new HashMap<>();
        }
        return filteredTransactions;
    }
}
