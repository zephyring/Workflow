package com.alwjeflkajwelkf.config;

import com.alwjeflkajwelkf.db.InMemJobStatusStore;
import com.alwjeflkajwelkf.db.InMemJobStore;
import com.alwjeflkajwelkf.db.InMemWorkflowStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:42 PM
 */
@Configuration
public class PersistenceConfig {
    private static final Logger LOG = LoggerFactory.getLogger(PersistenceConfig.class);

    @Bean
    public InMemJobStore inMemJobStore() {
        return new InMemJobStore();
    }

    @Bean
    public InMemWorkflowStore inMemWorkflowStore() {
        return new InMemWorkflowStore();
    }

    @Bean
    public InMemJobStatusStore inMemJobStatusStore() {
        return new InMemJobStatusStore();
    }
}
