package ru.sulakov.exchangeservice.parser;

import ru.sulakov.exchangeservice.model.ExchangePointData;
import ru.sulakov.exchangeservice.model.ExchangePointType;
import org.jsoup.select.Elements;

import java.util.List;

public interface BankExchangeDataParser {
    List<ExchangePointData> parse(Elements bankRowElements, ExchangePointType exchangePointType);
}
