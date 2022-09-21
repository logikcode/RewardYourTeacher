package com.decagon.rewardyourteacherapi.repository;

import com.decagon.rewardyourteacherapi.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ifeoluwa on 19/09/2022
 * @project
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
