package com.kinwae.challenge.codingChallenge.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
