package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.Currency;
import com.lukas.minibank.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final RestTemplateBuilder builder;
    private final RestTemplate restTemplate;
    private static final String BASE = "EUR";
    private static final String ACCESS_KEY = "0a0787627afc1491a244d24ed704d380";

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, RestTemplateBuilder builder) {
        this.currencyRepository = currencyRepository;
        this.builder = builder;
        this.restTemplate = builder.build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public List<Currency> getAllCurrencies(){
        Iterable<Currency> currencies = this.currencyRepository.findAll();
        List<Currency> currenciesList = new ArrayList<>();
        currencies.forEach(currenciesList::add);
        return currenciesList;
    }
    public Map<String, Currency> getAllCurrenciesMap(){
        Iterable<Currency> currencies = this.currencyRepository.findAll();
        Map<String, Currency> currenciesMap = new HashMap<>();
        currencies.forEach(currency -> {
            currenciesMap.put(currency.getCode(), currency);
        });
        return currenciesMap;
    }

    public boolean updateCurrencies() {
        Map<String, Currency> currenciesMap = getAllCurrenciesMap();
        String url = ("http://data.fixer.io/api/latest" +
                        "?access_key=" + ACCESS_KEY +
                        "&base=" + BASE);

        Map<String, Object> fixerMap = null;
        boolean success = false;
        try {
            fixerMap = this.restTemplate.getForObject(url, Map.class);
            success = new Boolean(fixerMap.get("success").toString());
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        if (success) {
            Map<String, Object> rates = (Map<String, Object>)fixerMap.get("rates");

            for (String key : rates.keySet()) {
                Currency currency;
                if (currenciesMap.containsKey(key)) {
                    currency = currenciesMap.get(key);
                } else {
                    currency = new Currency();
                   currency.setCode(key);
                }
                String rate = rates.get(key).toString();
                currency.setRate(new BigDecimal(rate));
                currency.setLastUpdated(ZonedDateTime.now());
                this.currencyRepository.save(currency);
            }
        }

        return success;
    }
}
