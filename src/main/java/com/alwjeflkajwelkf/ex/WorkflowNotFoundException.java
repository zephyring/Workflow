package com.alwjeflkajwelkf.ex;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:46 PM
 */
public class WorkflowNotFoundException extends RuntimeException {

    public WorkflowNotFoundException() {
    }

    public WorkflowNotFoundException(String message) {
        super(message);
    }

    public WorkflowNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkflowNotFoundException(Throwable cause) {
        super(cause);
    }

    public WorkflowNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
