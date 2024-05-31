package com.budget.planning.configuration;

import com.budget.planning.dto.response.AccountUpdateDTO;
import com.budget.planning.model.BankAccount;

public class Mapper {
    public static AccountUpdateDTO mapToAccountRegistration(BankAccount bankAccount) {
        return AccountUpdateDTO.builder()
                .account_id(bankAccount.getId())
                .balance(bankAccount.getBalance())
                .build();
    }
}
