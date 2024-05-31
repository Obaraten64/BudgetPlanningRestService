package com.budget.planning.service;

import com.budget.planning.configuration.Mapper;
import com.budget.planning.dto.request.AccountRegistrationRequest;
import com.budget.planning.dto.response.AccountRegistrationDTO;
import com.budget.planning.model.BankAccount;
import com.budget.planning.model.User;
import com.budget.planning.repository.BankAccountRepository;
import com.budget.planning.repository.BankHistoryRepository;
import com.budget.planning.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BudgetPlanningService {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankHistoryRepository bankHistoryRepository;

    @Transactional
    public AccountRegistrationDTO registerAccount(AccountRegistrationRequest accountRequest, User user) {
        BankAccount bankAccount = BankAccount.builder().balance(accountRequest.getBalance()).build();
        bankAccountRepository.save(bankAccount);

        user.setBankAccount(bankAccount);
        userRepository.save(user);

        return Mapper.mapToAccountRegistration(bankAccount);
    }
}
