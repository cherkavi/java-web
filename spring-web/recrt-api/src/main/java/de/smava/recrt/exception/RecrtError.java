package de.smava.recrt.exception;

public class RecrtError {

    protected int code;
    protected String message;

    public RecrtError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RecrtError(String message) {
        this(400, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
