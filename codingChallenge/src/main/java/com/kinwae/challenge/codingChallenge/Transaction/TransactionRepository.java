package com.kinwae.challenge.codingChallenge.Transaction;


import com.kinwae.challenge.codingChallenge.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {
   @Query(value = "SELECT * from Transaction where date=:date || narration like %:text% || amount=:text",nativeQuery = true)
     Page<Transaction> findAll(@Param("text") String text,@Param("date") String date, Pageable pageable);
    @Query(value = "SELECT * from Transaction where date=:date || user_id =:user || amount=:text",nativeQuery = true)
    Page<Transaction>  findByUser(@Param("user") User user, @Param("text") String text,@Param("date") String date,Pageable paging);

    Page<Transaction> findAll(Pageable pageable);
    @Query(value = "SELECT * from Transaction where user_id =:user",nativeQuery = true)
    Page<Transaction> findByUser(@Param("user") User user,Pageable pageable);
}
