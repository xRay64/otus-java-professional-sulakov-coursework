package ru.sulakov.exchangeservice.servlet;

import com.google.gson.Gson;
import ru.sulakov.exchangeservice.dto.BestExchangeRateDTO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.sulakov.exchangeservice.service.BestExchangeRatesProvider;
import ru.sulakov.exchangeservice.model.ExchangePointType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class ExchangeServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ExchangeServlet.class);
    private final Gson gson;
    private final BestExchangeRatesProvider bestExchangeRatesProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Inside doGet: {}", req.getQueryString());
        if (
                req.getQueryString() != null &&
                req.getQueryString().contains("pwd=securityPass")
        ) {
            BestExchangeRateDTO bestExchangeRates = bestExchangeRatesProvider.getBestExchangeRates(
                    Map.of(
//                            "https://rate.am/ru/armenian-dram-exchange-rates/banks/cash", ExchangePointType.BANK,
                            "https://rate.am/ru/armenian-dram-exchange-rates/exchange-points/cash/corporate", ExchangePointType.EXCHANGE_OFFICE
                    )
            );

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF8");
            resp.getWriter().println(gson.toJson(bestExchangeRates));
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
