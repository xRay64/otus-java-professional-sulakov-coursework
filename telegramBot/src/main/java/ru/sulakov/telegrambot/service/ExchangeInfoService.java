package ru.sulakov.telegrambot.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.sulakov.telegrambot.config.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeInfoService {
    private static final Logger log = LoggerFactory.getLogger(ExchangeInfoService.class);
    private final AppConfig config;
    public Optional<String> getInfoMessageText() {
        String result;
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        StringBuilder sb = new StringBuilder();
        String url = sb
                .append(config.getHost())
                .append(config.getPath())
                .append(sb.toString().endsWith("?") ? "pwd=" : "?pwd=")
                .append(config.getPassword())
                .toString();

        log.debug("ExchangeService url: {}", url);

        try (InputStream inputStream = new URL(url).openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);

            log.debug("Get JSON from ExchangeService: \n{}", jsonObject);

            JsonObject roubleBestPointJson = jsonObject.get("roubleBestPoint").getAsJsonObject();
            JsonObject dollarBestPointJson = jsonObject.get("dollarBestPoint").getAsJsonObject();

            float bestExchangeRate = jsonObject.get("bestExchangeRate").getAsFloat();

            String roublePointType = getPointTypeString(roubleBestPointJson.get("exchangePointType").getAsString());
            String roublePointName = roubleBestPointJson.get("bankName").getAsString();
            int roublePointBranchCount = roubleBestPointJson.get("branchesCount").getAsInt();
            float rubRate = roubleBestPointJson.get("RUBRate").getAsFloat();

            String dollarPointType = getPointTypeString(dollarBestPointJson.get("exchangePointType").getAsString());
            String dollarPointName = dollarBestPointJson.get("bankName").getAsString();
            int dollarPointBranchCount = dollarBestPointJson.get("branchesCount").getAsInt();
            float usdRate = dollarBestPointJson.get("USDRate").getAsFloat();

            result = String.format(
                    "Можем получить курс: :money_with_wings:*%.2f* рублей за доллар\n\n" +
                            ":ru:Продать рубли в %s \"%s\" у которого филиалов: %d, по крусу: %.2f \uD83C\uDDE6\uD83C\uDDF2AMD за руб\n\n" +
                            ":us:Купить доллары в %s \"%s\"  у которого филиалов: %d, по крусу: %.2f \uD83C\uDDE6\uD83C\uDDF2AMD за :heavy_dollar_sign:",
                    bestExchangeRate,

                    roublePointType,
                    roublePointName,
                    roublePointBranchCount,
                    rubRate,

                    dollarPointType,
                    dollarPointName,
                    dollarPointBranchCount,
                    usdRate
                    );
            result = EmojiParser.parseToUnicode(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(result);
    }

    private String getPointTypeString(String jsonPointTypeString) {
        return "BANK".equals(jsonPointTypeString) ? "банке" : "обменнике";
    }
}
