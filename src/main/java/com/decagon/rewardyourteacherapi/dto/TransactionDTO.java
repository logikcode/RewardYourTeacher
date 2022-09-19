package com.decagon.rewardyourteacherapi.dto;

import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author ifeoluwa on 18/09/2022
 * @project
 */

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    private TransactionType transactionType;
    private Long amount;
    private User user;
}
