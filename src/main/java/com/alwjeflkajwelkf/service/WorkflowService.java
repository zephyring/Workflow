package com.alwjeflkajwelkf.service;

import com.alwjeflkajwelkf.db.InMemJobStatusStore;
import com.alwjeflkajwelkf.db.InMemJobStore;
import com.alwjeflkajwelkf.db.InMemWorkflowStore;
import com.alwjeflkajwelkf.ex.NotFoundException;
import com.alwjeflkajwelkf.ex.WorkflowExistedException;
import com.alwjeflkajwelkf.executors.WorkflowExecutor;
import com.alwjeflkajwelkf.model.Job;
import com.alwjeflkajwelkf.model.SimplePrintNameJob;
import com.alwjeflkajwelkf.model.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:21 PM
 */
public class WorkflowService {
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowService.class);

    private InMemJobStore inMemJobStore;
    private InMemWorkflowStore inMemWorkflowStore;
    private InMemJobStatusStore inMemJobStatusStore;
    private WorkflowExecutor workflowExecutor;

    public WorkflowService(
        InMemJobStore inMemJobStore,
        InMemWorkflowStore inMemWorkflowStore,
        InMemJobStatusStore inMemJobStatusStore,
        WorkflowExecutor workflowExecutor
    ) {
        this.inMemJobStore = inMemJobStore;
        this.inMemWorkflowStore = inMemWorkflowStore;
        this.inMemJobStatusStore = inMemJobStatusStore;
        this.workflowExecutor = workflowExecutor;
    }

    public Workflow getWorkflow(String name) {
        return inMemWorkflowStore.getWorkflow(name);
    }

    public Workflow createWorkflow(String name) {
        Workflow ret = getWorkflow(name);

        if (ret != null) {
            throw new WorkflowExistedException("Workflow " + name + " already existed");
        }

        ret = new Workflow(name);
        inMemWorkflowStore.putWorkflow(ret);

        return ret;
    }

    public Job getOrCreateJob(String name) {
        Job ret = inMemJobStore.getJob(name);

        if (ret == null) {
            ret = new SimplePrintNameJob(name, inMemJobStatusStore);
            inMemJobStore.putJob(ret);
        }

        return ret;
    }

    public void registerJob(Workflow workflow, Job job) {
        registerJob(workflow, job, null);
    }

    public void registerJob(Workflow workflow, Job job, List<Job> dependencies) {
        if (dependencies != null) {
            job.registerDependencies(dependencies);
        }

        workflow.getJobs().add(job);
    }

    public void executeWorkflow(String name) {
        Workflow wf = getWorkflow(name);

        if (wf == null) {
            throw new NotFoundException("Workflow " + name + " not found");
        }

        workflowExecutor.execute(wf);
    }
}
