package com.budget.planning.service;

import com.budget.planning.repository.BankAccountRepository;
import com.budget.planning.repository.BankHistoryRepository;
import com.budget.planning.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BudgetPlanningService {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankHistoryRepository bankHistoryRepository;


}
