package com.interview.api;

import lombok.Data;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:48 PM
 */
@Data
public class WorkflowResponse<T> {
    private T payload;

    public WorkflowResponse() {
    }

    public WorkflowResponse(T payload) {
        this.payload = payload;
    }
}
