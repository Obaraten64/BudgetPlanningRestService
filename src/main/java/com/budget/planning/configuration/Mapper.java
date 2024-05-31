package com.budget.planning.configuration;

import com.budget.planning.dto.response.AccountRegistrationDTO;
import com.budget.planning.model.BankAccount;

public class Mapper {
    public static AccountRegistrationDTO mapToAccountRegistration(BankAccount bankAccount) {
        return AccountRegistrationDTO.builder()
                .account_id(bankAccount.getId())
                .balance(bankAccount.getBalance())
                .build();
    }
}
