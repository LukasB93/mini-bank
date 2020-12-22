package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

}
