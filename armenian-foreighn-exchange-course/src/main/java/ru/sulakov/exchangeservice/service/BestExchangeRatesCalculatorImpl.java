package ru.sulakov.exchangeservice.service;

import ru.sulakov.exchangeservice.dto.BestExchangeRateDTO;
import ru.sulakov.exchangeservice.model.ExchangePointData;

import java.util.ArrayList;
import java.util.List;

public class BestExchangeRatesCalculatorImpl implements BestExchangeRatesCalculator {

    @Override
    public BestExchangeRateDTO getBestExchangeRates(List<List<ExchangePointData>> dataLists) {
        List<ExchangePointData> listOfData = new ArrayList<>();
        dataLists.forEach(listOfData::addAll);
        return new BestExchangeRateDTO(
                getBestDollarRate(listOfData)
                ,getBestRoubleRate(listOfData)
        );
    }

    private ExchangePointData getBestDollarRate(List<ExchangePointData> listOfData) {
        List<ExchangePointData> sortedByDollarRate = new ArrayList<>(listOfData);

        sortedByDollarRate.sort((o1, o2) -> {
            float firstUsdRate = o1.getUSDRate();
            float secondUsdRate = o2.getUSDRate();
            int firstBranchesCount = o1.getBranchesCount();
            int secondBranchesCount = o2.getBranchesCount();

            if (firstUsdRate < secondUsdRate) {
                return -1;
            } else if (firstUsdRate == secondUsdRate) {
                return Integer.compare(secondBranchesCount, firstBranchesCount);
            }

            return 1;
        });

        ExchangePointData result = null;
        for (int i = 0; i < sortedByDollarRate.size(); i++) {
            if (sortedByDollarRate.get(i).getUSDRate() != 0) {
                result = sortedByDollarRate.get(i);
                break;
            }
        }

        return result;
    }

    private ExchangePointData getBestRoubleRate(List<ExchangePointData> listOfData) {
        List<ExchangePointData> sortedByRoubleRate = new ArrayList<>(listOfData);

        sortedByRoubleRate.sort((o1, o2) -> {
            float firstRubRate = o1.getRUBRate();
            float secondRubRate = o2.getRUBRate();
            int firstBranchesCount = o1.getBranchesCount();
            int secondBranchesCount = o2.getBranchesCount();

            if (firstRubRate < secondRubRate) {
                return 1;
            } else if (firstRubRate == secondRubRate) {
                return Integer.compare(secondBranchesCount, firstBranchesCount);
            }

            return -1;
        });

        ExchangePointData result = null;
        for (int i = 0; i < sortedByRoubleRate.size(); i++) {
            if (sortedByRoubleRate.get(i).getRUBRate() != 0) {
                result = sortedByRoubleRate.get(i);
                break;
            }
        }

        return result;
    }
}
