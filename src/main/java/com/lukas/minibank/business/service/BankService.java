package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AccountTransaction;
import com.lukas.minibank.data.entity.BankAccount;
import com.lukas.minibank.data.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    private final BankAccountRepository bankAccountRepository;
    private final EntityManager entityManager;

    @Autowired
    public BankService(BankAccountRepository bankAccountRepository, EntityManager entityManager) {
        this.bankAccountRepository = bankAccountRepository;
        this.entityManager = entityManager;
    }

    public List<BankAccount> getBankAccounts(){
        Iterable<BankAccount> bankAccounts = this.bankAccountRepository.findAll();
        List<BankAccount> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    public Optional<BankAccount> getBankAccountById(long bankAccountId){
        return this.bankAccountRepository.findById(bankAccountId);
    }

    public List<BankAccount> getBankAccountsByUserId(long userId) {
        String sql = "SELECT ba FROM " + BankAccount.class.getName() + " AS ba "
                + "WHERE ba.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, BankAccount.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<AccountTransaction> getAccountTransactionsByBankAccountId(long bankAccountId) {
        String sql = "SELECT at FROM " + AccountTransaction.class.getName() + " AS at "
                + "WHERE at.toAccount.baId = :bankAccountId "
                + "OR at.fromAccount.baId = :bankAccountId "
                + "ORDER BY time DESC ";

        Query query = this.entityManager.createQuery(sql, AccountTransaction.class);
        query.setParameter("bankAccountId", bankAccountId);
        return query.getResultList();
    }

    public Optional<BankAccount> getBankAccountsById(long baId) {
        Optional<BankAccount> bankAccount = this.bankAccountRepository.findById(baId);

        return bankAccount;
    }

    public void addToBalance(BankAccount bankAccount, BigDecimal amount) {
        bankAccount.setBalance( bankAccount.getBalance().add(amount));
        this.bankAccountRepository.save(bankAccount);
    }
}
