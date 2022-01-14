package com.kinwae.challenge.codingChallenge.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public void registerUser(User user){
        userRepository.save(user);
    }
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
