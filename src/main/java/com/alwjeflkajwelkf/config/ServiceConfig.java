package com.alwjeflkajwelkf.config;

import com.alwjeflkajwelkf.db.InMemJobStatusStore;
import com.alwjeflkajwelkf.db.InMemJobStore;
import com.alwjeflkajwelkf.db.InMemWorkflowStore;
import com.alwjeflkajwelkf.executors.WorkflowExecutor;
import com.alwjeflkajwelkf.service.WorkflowService;
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
