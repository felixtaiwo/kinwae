package com.kinwae.challenge.codingChallenge.security;

import com.kinwae.challenge.codingChallenge.User.User;
import com.kinwae.challenge.codingChallenge.User.UserService;
import com.kinwae.challenge.codingChallenge.Utils.HelperFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthenticationController {
    @Autowired
    UserService service;
    @PersistenceContext
    private EntityManager entityManager;
    BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
    @Transactional
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User credentials, HttpServletRequest request) throws IOException, SQLException {
        if(!authenticateUser(credentials)){
            request.getSession().invalidate();
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }
        try{
            int user =(int) request.getSession().getAttribute("USER");
            return new ResponseEntity<>( "Already logged in on this device",HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            User user=service.findUserByEmail(credentials.getEmail());
            if(isLoggedInOnAnotherDevice(user)){
                if(user.getAge()>40){
                    request.getSession().invalidate();
                    return new ResponseEntity<>( successfulLoginBody(user),HttpStatus.OK);
                }else{
                    request.getSession().invalidate();
                    return new ResponseEntity<>( "Already Logged in on another device",HttpStatus.BAD_REQUEST);}
            }
            request.getSession().setAttribute("USER",user.getId());
            return new ResponseEntity<>( successfulLoginBody(user),HttpStatus.OK);
        }
    }
    @Transactional
    private void maauallyinvalIdateSession(User user) throws IOException, SQLException {
        Query query = entityManager.createNativeQuery("select * from spring_session_attributes"+";");
        List<Object> session = query.getResultList();
        for(Object s:session){
           HttpSession ss= (HttpSession) s;
            if((int)ss.getAttribute("USER")==user.getId()){
                query = entityManager.createNativeQuery("delete from spring_session where SESSION_ID="+"'"+ss.getId()+"';");
                query.executeUpdate();
            }
        }
    }

    private  Map<String,String> successfulLoginBody(User user) {
        Map<String,String> res = new HashMap<>();
        res.put("email",user.getEmail());
        res.put("firstname",user.getFirstname());
        res.put("lastname",user.getLastname());
        return res;
    }
    @Transactional
    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    public void logout(HttpServletRequest request) throws IOException {
        request.getSession().invalidate();
        Query query = entityManager.createNativeQuery("delete from spring_session where SESSION_ID="+"'"+request.getSession().getId()+"';");
        query.executeUpdate();

    }
    private boolean authenticateUser(User user){
        User user1 =service.findUserByEmail(user.getEmail());
        if(user1==null){
            return false;
        }
        return bCryptPasswordEncoder.matches(user.getPassword(),service.findUserByEmail(user.getEmail()).getPassword());
    }
    private boolean isLoggedInOnAnotherDevice(User user) throws IOException {
        Query query = entityManager.createNativeQuery("select * from spring_session_attributes where ATTRIBUTE_BYTES=:user");
        query.setParameter("user", HelperFunc.objectToByte(user.getId()));
        return query.getResultList().size() > 0;
    }
}
