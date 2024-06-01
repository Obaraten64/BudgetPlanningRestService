package com.budget.planning.service;

import com.budget.planning.configuration.Mapper;
import com.budget.planning.configuration.security.Role;
import com.budget.planning.dto.request.AccountRegistrationRequest;
import com.budget.planning.dto.request.AccountUpdateRequest;
import com.budget.planning.dto.request.LimitUpdateRequest;
import com.budget.planning.dto.response.AccountUpdateDTO;
import com.budget.planning.dto.response.BankHistoryDTO;
import com.budget.planning.dto.response.UserWithLimitDTO;
import com.budget.planning.exception.AccountUpdateException;
import com.budget.planning.exception.BankHistoryException;
import com.budget.planning.exception.LimitUpdateException;
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
import java.util.List;
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
                .reason(accountRequest.getReason())
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
                .reason(accountRequest.getReason())
                .timestamp(LocalDateTime.now())
                .amount(accountRequest.getAmount())
                .bankAccount(bankAccount)
                .user(user)
                .build();
        bankHistoryRepository.save(bankHistory);

        return Mapper.mapToAccountRegistration(bankAccount);
    }

    @Transactional
    public UserWithLimitDTO updateLimit(LimitUpdateRequest limitRequest, User user) {
        User child = userRepository.findUserByEmail(limitRequest.getUsername())
                .orElseThrow(() -> new LimitUpdateException("No user with such username"));
        if (!child.getBankAccount().equals(user.getBankAccount()) || !Role.CHILD.equals(child.getRole())) {
            throw new LimitUpdateException("You can't change limit of this user");
        }

        child.setUsage_limit(limitRequest.getUsage_limit());
        userRepository.save(child);

        return Mapper.mapToUserWithLimitDTO(child);
    }

    public List<BankHistoryDTO> getAccountHistory(User user) {
        BankAccount bankAccount = Optional.ofNullable(user.getBankAccount())
                .orElseThrow(() -> new BankHistoryException("You do not have a bank account!"));

        List<BankHistory> bankHistories = bankHistoryRepository.findAllHistoriesByBankAccount(bankAccount);
        if (bankHistories.isEmpty()) {
            throw new BankHistoryException("No transactions have been performed for this account");
        }

        return bankHistories.stream()
                .map(Mapper::mapToBankHistoryDTO)
                .toList();
    }
}
