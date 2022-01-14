package com.kinwae.challenge.codingChallenge.Transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kinwae.challenge.codingChallenge.User.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Transaction implements Serializable {
    public enum TransactionType {DEBIT,CREDIT};
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Date date;
    private String narration;
    private double amount;
    private TransactionType transactionType;
    @ManyToOne
    private User user;

    public Transaction() {
    }

    public Transaction(int id, Date date, String narration, double amount, TransactionType transactionType, User user) {
        this.id = id;
        this.date = date;
        this.narration = narration;
        this.amount = amount;
        this.transactionType = transactionType;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
