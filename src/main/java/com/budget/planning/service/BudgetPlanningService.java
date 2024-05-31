package com.budget.planning.service;

import com.budget.planning.configuration.Mapper;
import com.budget.planning.dto.request.AccountRegistrationRequest;
import com.budget.planning.dto.request.AccountUpdateRequest;
import com.budget.planning.dto.response.AccountUpdateDTO;
import com.budget.planning.exception.AccountUpdateException;
import com.budget.planning.model.BankAccount;
import com.budget.planning.model.BankHistory;
import com.budget.planning.model.User;
import com.budget.planning.repository.BankAccountRepository;
import com.budget.planning.repository.BankHistoryRepository;
import com.budget.planning.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BudgetPlanningService {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankHistoryRepository bankHistoryRepository;

    @Transactional
    public AccountUpdateDTO registerAccount(AccountRegistrationRequest accountRequest, User user) {
        BankAccount bankAccount = BankAccount.builder().balance(accountRequest.getBalance()).build();
        bankAccountRepository.save(bankAccount);

        user.setBankAccount(bankAccount);
        userRepository.save(user);

        return Mapper.mapToAccountRegistration(bankAccount);
    }

    @Transactional
    public AccountUpdateDTO replenishAccount(AccountUpdateRequest accountRequest, User user) {
        BankAccount bankAccount = Optional.ofNullable(user.getBankAccount())
                .orElseThrow(() -> new AccountUpdateException("You do not have a bank account!"));
        bankAccount.setBalance(bankAccount.getBalance() + accountRequest.getAmount());
        bankAccountRepository.save(bankAccount);

        BankHistory bankHistory = BankHistory.builder()
                .operation("replenish")
                .purpose(accountRequest.getPurpose())
                .timestamp(LocalDateTime.now())
                .amount(accountRequest.getAmount())
                .bankAccount(bankAccount)
                .user(user)
                .build();
        bankHistoryRepository.save(bankHistory);

        return Mapper.mapToAccountRegistration(bankAccount);
    }

    @Transactional
    public AccountUpdateDTO withdrawAccount(AccountUpdateRequest accountRequest, User user) {
        if (accountRequest.getAmount() > user.getUsage_limit()) {
            throw new AccountUpdateException("Your usage limit does not allow you to perform this operation");
        }
        BankAccount bankAccount = Optional.ofNullable(user.getBankAccount())
                .orElseThrow(() -> new AccountUpdateException("You do not have a bank account!"));

        int newBalance = bankAccount.getBalance() - accountRequest.getAmount();
        if (newBalance < 0) {
            throw new AccountUpdateException("Balance can not become less than zero after operation");
        }

        bankAccount.setBalance(newBalance);
        bankAccountRepository.save(bankAccount);

        BankHistory bankHistory = BankHistory.builder()
                .operation("withdraw")
                .purpose(accountRequest.getPurpose())
                .timestamp(LocalDateTime.now())
                .amount(accountRequest.getAmount())
                .bankAccount(bankAccount)
                .user(user)
                .build();
        bankHistoryRepository.save(bankHistory);

        return Mapper.mapToAccountRegistration(bankAccount);
    }
}
