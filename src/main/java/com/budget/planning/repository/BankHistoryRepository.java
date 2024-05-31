package com.budget.planning.repository;

import com.budget.planning.model.BankHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankHistoryRepository extends JpaRepository<BankHistory,Long> {

}
