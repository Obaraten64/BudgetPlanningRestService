package com.budget.planning.configuration;

import com.budget.planning.dto.response.AccountUpdateDTO;
import com.budget.planning.dto.response.BankAccountDTO;
import com.budget.planning.dto.response.BankHistoryDTO;
import com.budget.planning.dto.response.UserWithLimitDTO;
import com.budget.planning.model.BankAccount;
import com.budget.planning.model.BankHistory;
import com.budget.planning.model.User;

public class Mapper {
    public static AccountUpdateDTO mapToAccountRegistration(BankAccount bankAccount) {
        return AccountUpdateDTO.builder()
                .account_id(bankAccount.getId())
                .balance(bankAccount.getBalance())
                .build();
    }

    public static UserWithLimitDTO mapToUserWithLimitDTO(User user) {
        return UserWithLimitDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .usage_limit(user.getUsage_limit())
                .build();
    }

    public static BankHistoryDTO mapToBankHistoryDTO(BankHistory bankHistory) {
        return BankHistoryDTO.builder()
                .operation(bankHistory.getOperation())
                .reason(bankHistory.getReason())
                .timestamp(bankHistory.getTimestamp())
                .amount(bankHistory.getAmount())
                .user(Mapper.mapToUserWithLimitDTO(bankHistory.getUser()))
                .build();
    }

    public static BankAccountDTO mapToBankAccountDTO(BankAccount bankAccount) {
        return BankAccountDTO.builder()
                .id(bankAccount.getId())
                .balance(bankAccount.getBalance())
                .build();
    }
}
