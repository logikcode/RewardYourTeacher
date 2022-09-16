package com.decagon.rewardyourteacherapi.repository;


import com.decagon.rewardyourteacherapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    @Query("select n from Transaction n where n.user.id=:userId ORDER BY n.createDate DESC")
//    List<Transaction> userTransactions(Long userId);

}
