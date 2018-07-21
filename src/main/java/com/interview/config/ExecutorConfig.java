package com.interview.config;

import com.interview.executors.SimpleAsyncWorkflowExecutor;
import com.interview.executors.WorkflowExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 4:53 PM
 */
@Configuration
@EnableConfigurationProperties(ExecutorProps.class)
public class ExecutorConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ExecutorConfig.class);

    @Bean
    public WorkflowExecutor workflowExecutor(
        ExecutorProps props
    ) {
        return new SimpleAsyncWorkflowExecutor(
            props.getConcurrency()
        );
    }
}
