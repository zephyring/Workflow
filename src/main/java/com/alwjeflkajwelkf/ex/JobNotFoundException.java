package com.alwjeflkajwelkf.ex;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:46 PM
 */
public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException() {
    }

    public JobNotFoundException(String message) {
        super(message);
    }

    public JobNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobNotFoundException(Throwable cause) {
        super(cause);
    }

    public JobNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
