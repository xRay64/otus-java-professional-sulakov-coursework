package ru.sulakov.exchangeservice.exception;

public class SiteDataGetException extends RuntimeException {
    public SiteDataGetException() {
        super();
    }

    public SiteDataGetException(String message) {
        super(message);
    }

    public SiteDataGetException(String message, Throwable cause) {
        super(message, cause);
    }

    public SiteDataGetException(Throwable cause) {
        super(cause);
    }
}
