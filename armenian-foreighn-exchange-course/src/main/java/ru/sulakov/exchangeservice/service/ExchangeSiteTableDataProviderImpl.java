package ru.sulakov.exchangeservice.service;

import ru.sulakov.exchangeservice.exception.SiteDataGetException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ExchangeSiteTableDataProviderImpl implements ExchangeSiteTableDataProvider {
    @Override
    public Elements getPageTableRowsElements(String uri) {
        Elements tableRows = null;
        try {
            Document document = Jsoup.connect(uri).get();
            tableRows =
                    document.select("table#rb").get(0).child(0).children();
        } catch (NullPointerException | IOException e) {
            throw new SiteDataGetException(e);
        }
        return tableRows;
    }
}
