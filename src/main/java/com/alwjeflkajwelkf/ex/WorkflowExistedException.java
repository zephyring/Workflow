package com.alwjeflkajwelkf.ex;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 4:24 PM
 */
public class WorkflowExistedException extends RuntimeException {

    public WorkflowExistedException() {
    }

    public WorkflowExistedException(String message) {
        super(message);
    }

    public WorkflowExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowExistedException(Throwable cause) {
        super(cause);
    }

    public WorkflowExistedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
