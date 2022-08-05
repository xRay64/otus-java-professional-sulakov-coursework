FROM openjdk:17-oracle

COPY ./target/telegram_bot.jar /usr/share/telegram_bot/


WORKDIR /usr/share/telegram_bot

CMD java -jar /usr/share/telegram_bot/telegram_bot.jar