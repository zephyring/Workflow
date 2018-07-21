package com.interview.config;

import com.interview.db.InMemJobStatusStore;
import com.interview.db.InMemJobStore;
import com.interview.db.InMemWorkflowStore;
import com.interview.executors.WorkflowExecutor;
import com.interview.service.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 8:29 PM
 */
@Configuration
public class ServiceConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceConfig.class);

    @Bean
    public WorkflowService workflowService(
        InMemJobStore inMemJobStore,
        InMemWorkflowStore inMemWorkflowStore,
        InMemJobStatusStore inMemJobStatusStore,
        WorkflowExecutor workflowExecutor
    ) {
        return new WorkflowService(
            inMemJobStore,
            inMemWorkflowStore,
            inMemJobStatusStore,
            workflowExecutor
        );
    }
}
