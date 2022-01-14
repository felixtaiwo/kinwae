package com.kinwae.challenge.codingChallenge.User;


import com.kinwae.challenge.codingChallenge.Transaction.Transaction;
import com.kinwae.challenge.codingChallenge.Transaction.TransactionService;
import com.kinwae.challenge.codingChallenge.User.User;
import com.kinwae.challenge.codingChallenge.Utils.EmailSender;
import com.kinwae.challenge.codingChallenge.Utils.HelperFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    TransactionService transactionService;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody User user,HttpServletRequest request){
        request.getSession().invalidate();
        Boolean isEmailvalid =HelperFunc.EmailValidator(user.getEmail());
        if(isEmailvalid&&user.getAge()>=18){
            user.setPassword(encoder.encode(user.getPassword()));
            service.registerUser(user);
            user=service.findUserByEmail(user.getEmail());
            transactionService.addTransactions(HelperFunc.generateTransaction(user));
            EmailSender emailSender = new EmailSender(HelperFunc.registrationEmail(user),user.getEmail(),"Registration Successful");
            Thread emailThread = new Thread(emailSender);
            emailThread.start();
            return new ResponseEntity<>("Registration Successful", HttpStatus.OK);
        }
        if(!isEmailvalid){
            return new ResponseEntity<>("Provide a corporate email address", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User's age is less than 18", HttpStatus.BAD_REQUEST);
    }

}
