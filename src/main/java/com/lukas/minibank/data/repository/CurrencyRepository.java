package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
