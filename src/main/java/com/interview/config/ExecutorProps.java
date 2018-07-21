package com.interview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 4:54 PM
 */
@Data
@ConfigurationProperties("executors")
public class ExecutorProps {
    private int concurrency;
}
