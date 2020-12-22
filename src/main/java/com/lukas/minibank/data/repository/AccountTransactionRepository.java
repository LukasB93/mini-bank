package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.AccountTransaction;
import org.springframework.data.repository.CrudRepository;

public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Long> {
}
