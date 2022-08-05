FROM openjdk:17-oracle

ARG SERVICE_USER=svc_exchange
ARG SERVICE_GROUP=svc_exchange
ARG SERVICE_GROUP_ID=9999999

COPY ./target/exchange_service-jar-with-dependencies.jar /usr/share/exchange_service/exchange_service.jar

RUN groupadd -r --gid ${SERVICE_GROUP_ID} ${SERVICE_GROUP}
RUN useradd -u 1000 -r -g ${SERVICE_GROUP_ID} -m -d /usr/share/exchange_service -s /sbin/nologin -c "Service user" ${SERVICE_USER}



WORKDIR /usr/share/exchange_service
EXPOSE 9900

CMD java -jar /usr/share/exchange_service/exchange_service.jar