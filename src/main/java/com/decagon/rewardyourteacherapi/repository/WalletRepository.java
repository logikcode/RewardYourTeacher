package com.decagon.rewardyourteacherapi.repository;

import com.decagon.rewardyourteacherapi.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByWalletAddress(String walletAddress);
    Wallet findByUser_Id(Long userId);
}

