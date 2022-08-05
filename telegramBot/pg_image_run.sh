docker run -d \
--name bot_postgres \
-e POSTGRES_USER=sulakov \
-e POSTGRES_PASSWORD=Ant1989_Sul \
-e POSTGRES_DB=telegram_bot \
-p 5432:5432 \
postgres