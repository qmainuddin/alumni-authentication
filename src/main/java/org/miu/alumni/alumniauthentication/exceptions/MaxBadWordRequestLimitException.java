package org.miu.alumni.alumniauthentication.exceptions;

public class MaxBadWordRequestLimitException extends RuntimeException {
    public MaxBadWordRequestLimitException(String message) {
        super(message);
    }
    public MaxBadWordRequestLimitException(String message, Throwable cause) {
        super(message, cause);
    }
    public MaxBadWordRequestLimitException() {
        super();
    }
}
