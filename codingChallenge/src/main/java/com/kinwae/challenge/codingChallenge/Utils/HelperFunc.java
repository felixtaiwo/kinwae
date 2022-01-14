package com.kinwae.challenge.codingChallenge.Utils;





import com.kinwae.challenge.codingChallenge.Transaction.Transaction;
import com.kinwae.challenge.codingChallenge.Transaction.TransactionController;
import com.kinwae.challenge.codingChallenge.Transaction.TransactionService;
import com.kinwae.challenge.codingChallenge.User.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HelperFunc {
    @Autowired
    TransactionService transactionService;
    static Random random = new Random();
    static DecimalFormat formatter = new DecimalFormat("#.##");
    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public static Boolean EmailValidator(String email){
        String provider = email.substring(email.indexOf("@") + 1, email.indexOf("."));
        return !provider.equals("yahoo") && !provider.equals("gmail") && !provider.equals("outlook");
    }

    public static String registrationEmail(User user){
        String message =
                "Dear "+ user.getFirstname() +", your registration to Kinwae was successful. Again, your details are :\n"+
                        "Firstname: "+user.getFirstname()+"\n"+
                        "Lastname: "+user.getLastname()+"\n"+
                        "Email: "+user.getEmail()+"\n"+
                        "Birth Date: "+user.getDataOfBirth()+"\n"+
                        "Age: "+user.getAge();
        return message;
    }
    public static List<Transaction> generateTransaction(User user){
        List<Transaction> transactions = new ArrayList<>();
        String[] transactionTypes={"DEBIT","CREDIT"};
        String[] debitNarrations ={"Transfer to AppWeb","Withdrwal","Flight Ticket"};
        String[] creditNarrations ={"Reversal from Opin Bank","Salary","Shares & Dividends","Loan Approved","Give Away"};
        int initialTransactionNum = random.nextInt(101)+200;/**This will generate a minimum of 200 transactions and a maximu of 300 transactions**/
        for(int i=0;i<initialTransactionNum;i++){
            Transaction transaction = new Transaction();
            transaction.setTransactionType(Transaction.TransactionType.valueOf(transactionTypes[random.nextInt(2)]));
            transaction.setAmount(Double.parseDouble(formatter.format(random.nextDouble()*100000)));
            transaction.setUser(user);
            transaction.setDate(dateGenerator());
            transaction.setNarration(transaction.getTransactionType().toString().equals("DEBIT")?debitNarrations[random.nextInt(debitNarrations.length)]:creditNarrations[random.nextInt(creditNarrations.length)]);
            transactions.add(transaction);
        }
        return transactions;
    }

    public static Date dateGenerator(){
        return new Date(121,random.nextInt(12),random.nextInt(29));
    }

    public static byte[] objectToByte(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte [] data = bos.toByteArray();
        return data;
    }
}
