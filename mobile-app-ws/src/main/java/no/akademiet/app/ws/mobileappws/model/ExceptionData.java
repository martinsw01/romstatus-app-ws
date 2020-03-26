package no.akademiet.app.ws.mobileappws.model;

public class ExceptionData {
    private final String header = "EXCEPTION";
    private Exception exception;

    public ExceptionData(Exception exception) {
        this.exception = exception;
    }

    public String getHeader() {
        return header;
    }

    public Exception getException() {
        return exception;
    }
}
