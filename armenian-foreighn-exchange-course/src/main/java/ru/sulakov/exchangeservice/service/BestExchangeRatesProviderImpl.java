package ru.sulakov.exchangeservice.service;

import ru.sulakov.exchangeservice.dto.BestExchangeRateDTO;
import lombok.RequiredArgsConstructor;
import ru.sulakov.exchangeservice.model.ExchangePointData;
import ru.sulakov.exchangeservice.model.ExchangePointType;
import ru.sulakov.exchangeservice.parser.BankExchangeDataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BestExchangeRatesProviderImpl implements BestExchangeRatesProvider {
    private final BankExchangeDataParser exchangeSiteDataParser;
    private final ExchangeSiteTableDataProvider siteDataProvider;
    private final BestExchangeRatesCalculator bestExchangeRatesCalculator;

    @Override
    public BestExchangeRateDTO getBestExchangeRates(Map<String, ExchangePointType> parseList) {

        List<List<ExchangePointData>> dataLists = new ArrayList<>();
        parseList.forEach((s, exchangePointType) -> {
            List<ExchangePointData> currentData =
                    exchangeSiteDataParser.parse(
                            siteDataProvider.getPageTableRowsElements(s)
                            ,exchangePointType
                    );
            dataLists.add(currentData);
        });

        return bestExchangeRatesCalculator.getBestExchangeRates(dataLists);
    }
}
