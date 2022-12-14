package com.decagon.rewardyourteacherapi.repository;


import com.decagon.rewardyourteacherapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transaction WHERE user_id = ?1 ORDER BY create_date DESC ", nativeQuery = true)
    List<Transaction> findUserTransactionHistory(Long userId);

}
