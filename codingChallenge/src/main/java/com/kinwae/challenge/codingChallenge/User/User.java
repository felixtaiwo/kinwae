package com.kinwae.challenge.codingChallenge.User;



import com.kinwae.challenge.codingChallenge.Utils.HelperFunc;
import com.kinwae.challenge.codingChallenge.Transaction.Transaction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private Date dataOfBirth;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Transaction>  transactions;
    @Transient
    private int age;

    public User() {
    }

    public User(int id, String firstname, String lastname, String email, Date dataOfBirth) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dataOfBirth = dataOfBirth;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Date dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        Period period = Period.between(HelperFunc.convertToLocalDate(dataOfBirth),HelperFunc.convertToLocalDate(new Date()));
        return period.getYears();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", dataOfBirth=" + dataOfBirth +
                ", password='" + password + '\'' +
                ", transactions=" + transactions +
                ", age=" + age +
                '}';
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
