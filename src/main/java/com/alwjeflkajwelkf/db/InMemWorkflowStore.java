package com.alwjeflkajwelkf.db;

import com.alwjeflkajwelkf.model.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:39 PM
 */
public class InMemWorkflowStore {
    private static final Logger LOG = LoggerFactory.getLogger(InMemWorkflowStore.class);

    private Map<String, Workflow> workflows = new ConcurrentHashMap<>();

    public void putWorkflow(Workflow workflow) {
        workflows.put(workflow.getName(), workflow);
    }

    public Workflow getWorkflow(String name) {
        return workflows.get(name);
    }

    public void clear() {
        workflows.clear();
    }
}
