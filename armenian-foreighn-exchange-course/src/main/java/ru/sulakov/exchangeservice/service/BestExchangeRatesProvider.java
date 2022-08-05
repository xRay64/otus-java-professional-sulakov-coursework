package ru.sulakov.exchangeservice.service;

import ru.sulakov.exchangeservice.dto.BestExchangeRateDTO;
import ru.sulakov.exchangeservice.model.ExchangePointType;

import java.util.Map;

public interface BestExchangeRatesProvider {
    BestExchangeRateDTO getBestExchangeRates(Map<String, ExchangePointType> parseList);
}
