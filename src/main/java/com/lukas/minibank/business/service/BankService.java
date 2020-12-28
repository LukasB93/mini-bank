package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.BankAccount;
import com.lukas.minibank.data.entity.UserRole;
import com.lukas.minibank.data.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

    public List<BankAccount> getBankAccountsByUserId(Long userId) {
        String sql = "SELECT ba from " + BankAccount.class.getName() + " AS ba "
                + "WHERE ba.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, BankAccount.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
