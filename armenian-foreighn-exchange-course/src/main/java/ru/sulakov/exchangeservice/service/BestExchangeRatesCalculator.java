package ru.sulakov.exchangeservice.service;

import ru.sulakov.exchangeservice.dto.BestExchangeRateDTO;
import ru.sulakov.exchangeservice.model.ExchangePointData;

import java.util.List;

public interface BestExchangeRatesCalculator {
    BestExchangeRateDTO getBestExchangeRates(List<List<ExchangePointData>> dataLists);
}
