package com.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:14 PM
 */
@SpringBootApplication
public class WorkflowManagerApp {
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowManagerApp.class);

    public static void main(String[] args) {
        SpringApplication.run(WorkflowManagerApp.class, args);
    }
}
