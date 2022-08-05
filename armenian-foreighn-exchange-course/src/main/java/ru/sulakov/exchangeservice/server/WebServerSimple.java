package ru.sulakov.exchangeservice.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.sulakov.exchangeservice.service.BestExchangeRatesProvider;
import ru.sulakov.exchangeservice.servlet.ExchangeServlet;


public class WebServerSimple implements WebServer {
    private final Gson gson;
    private final Server server;

    private final BestExchangeRatesProvider bestExchangeRatesProvider;

    public WebServerSimple(int port, Gson gson, BestExchangeRatesProvider bestExchangeRatesProvider) {
        this.gson = gson;
        this.bestExchangeRatesProvider = bestExchangeRatesProvider;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private void initContext() {

        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(servletContextHandler);

        server.setHandler(handlers);
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new ExchangeServlet(gson, bestExchangeRatesProvider)), "/usd_ru_best_rate");
        return servletContextHandler;
    }
}
