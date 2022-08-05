package ru.sulakov.exchangeservice.parser;

import ru.sulakov.exchangeservice.model.ExchangePointData;
import ru.sulakov.exchangeservice.model.ExchangePointType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankExchangeDataParserImpl implements BankExchangeDataParser {
    private static final Logger log = LoggerFactory.getLogger(BankExchangeDataParserImpl.class);

    @Override
    public List<ExchangePointData> parse(Elements bankRowElements, ExchangePointType exchangePointType) {
        List<ExchangePointData> resultList = new ArrayList<>();

        for (Element bankRowElement : bankRowElements) {
            Optional<ExchangePointData> bankExchangeData = parseElement(bankRowElement, exchangePointType);
            bankExchangeData.ifPresent(resultList::add);
        }
        return resultList;
    }

    private Optional<ExchangePointData> parseElement(Element bankRowElement, ExchangePointType exchangePointType) {
        String pointName = bankRowElement.select("td.bank > a").text();
        Elements rowCols = bankRowElement.children();
        if (rowCols.size() < 10) {
            return Optional.empty();
        }

        float usdSellRete;
        float rubBuyRate;
        int branchesCount;
        try {
            branchesCount = Integer.parseInt(rowCols.get(3).select("a").text());
            String dollarRateString = rowCols.get(6).text();
            String rubRateString = rowCols.get(9).text();

            if (!"".equals(dollarRateString)) {
                usdSellRete = Float.parseFloat(dollarRateString);
            } else {
                usdSellRete = 0F;
            }

            if (!"".equals(rubRateString)) {
                rubBuyRate = Float.parseFloat(rubRateString);
            } else {
                rubBuyRate = 0F;
            }
        } catch (NumberFormatException e) {
            log.error("Error parse rates");
            return Optional.empty();
        }

        return Optional.of(new ExchangePointData(pointName, usdSellRete, rubBuyRate, branchesCount, exchangePointType));
    }
}
