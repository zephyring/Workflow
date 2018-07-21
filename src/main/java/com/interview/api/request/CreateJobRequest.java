package com.interview.api.request;

import lombok.Data;

import java.util.List;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 4:05 PM
 */
@Data
public class CreateJobRequest {
    private String name;
    private List<String> dependencies;
}
