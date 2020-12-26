package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.AccountTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, Long> {
}
