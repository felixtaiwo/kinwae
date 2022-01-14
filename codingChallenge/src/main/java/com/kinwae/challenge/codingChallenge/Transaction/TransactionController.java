package com.kinwae.challenge.codingChallenge.Transaction;

import com.kinwae.challenge.codingChallenge.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @RequestMapping(method = RequestMethod.GET)
    public Object getUserTransaction(@RequestParam(defaultValue = "0",name = "page") int pageNo, @RequestParam(defaultValue = "0",name = "userid") int userid, HttpServletRequest request,
                                     @RequestParam(defaultValue = "",name = "text") String text,
    @RequestParam(defaultValue = "",name = "date") String date){
        if(userid==0  ||(int)request.getSession().getAttribute("USER") == userid){
            userid=(int)request.getSession().getAttribute("USER");
        } else{
            userid=0;

        }
        return transactionService.getTransactions(pageNo,userid,text,date);

    }

}
