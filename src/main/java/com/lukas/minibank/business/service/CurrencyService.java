package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.Currency;
import com.lukas.minibank.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final EntityManager entityManager;
    private final RestTemplate restTemplate;
    private static final String BASE = "EUR";
    private static final String ACCESS_KEY = "0a0787627afc1491a244d24ed704d380";

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, EntityManager entityManager, RestTemplateBuilder builder) {
        this.currencyRepository = currencyRepository;
        this.entityManager = entityManager;
        this.restTemplate = builder.build();
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
    public Currency getCurrencyByCode(String code){
        Currency currency = null;

        String sql = "SELECT c from " + Currency.class.getName() + " AS c "
                + "WHERE c.code = :code ";

        Query query = this.entityManager.createQuery(sql, Currency.class);
        query.setParameter("code", code);
        currency = (Currency)query.getSingleResult();

        return currency;
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
            Map<String, Object> fixerRates = (Map<String, Object>)fixerMap.get("rates");

            for (String currencyCode : fixerRates.keySet()) {
                Currency currency;
                if (currenciesMap.containsKey(currencyCode)) {
                    currency = currenciesMap.get(currencyCode);
                } else {
                    currency = new Currency();
                    currency.setCode(currencyCode);
                }
                String rate = fixerRates.get(currencyCode).toString();
                currency.setRate(new BigDecimal(rate));
                currency.setLastUpdated(ZonedDateTime.now());
                this.currencyRepository.save(currency);
            }
        }

        return success;
    }

    public BigDecimal convert(String sourceCurrencyCode, String endpointCurrencyCode, BigDecimal sourceAmount) {
        BigDecimal outcomeAmount;

        Currency sourceCurrency = this.getCurrencyByCode(sourceCurrencyCode);
        Currency endpointCurrency = this.getCurrencyByCode(endpointCurrencyCode);
        BigDecimal sourceRate = sourceCurrency.getRate();
        BigDecimal endpointRate = endpointCurrency.getRate();
        BigDecimal rate = endpointRate.divide(sourceRate, MathContext.DECIMAL32);
        outcomeAmount = sourceAmount.multiply(rate, MathContext.DECIMAL32);

        System.out.println("outcomeAmount: " + outcomeAmount);
        return outcomeAmount;
    }
}
