package com.alwjeflkajwelkf.executors;

import com.alwjeflkajwelkf.model.Job;
import com.alwjeflkajwelkf.model.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 4:50 PM
 */
public class SimpleAsyncWorkflowExecutor implements WorkflowExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleAsyncWorkflowExecutor.class);

    private ExecutorService executorService;

    public SimpleAsyncWorkflowExecutor(int concurrency) {
        this.executorService = Executors.newFixedThreadPool(20);
    }

    @Override
    public void execute(Workflow wf) {
        executorService.submit(() -> {
            LOG.info("Executing workflow " + wf.getName() + ":");

            wf.getJobs().forEach(Job::execute);

            LOG.info("Workflow Completed!!");
        });
    }
}
