package ru.sulakov.exchangeservice.service;

import org.jsoup.select.Elements;

public interface ExchangeSiteTableDataProvider {
    Elements getPageTableRowsElements(String uri);
}
