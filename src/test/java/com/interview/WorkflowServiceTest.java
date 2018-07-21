package com.interview;

import com.interview.config.ExecutorConfig;
import com.interview.config.PersistenceConfig;
import com.interview.config.ServiceConfig;
import com.interview.db.InMemJobStatusStore;
import com.interview.db.InMemJobStore;
import com.interview.db.InMemWorkflowStore;
import com.interview.ex.BadRequestException;
import com.interview.ex.NotFoundException;
import com.interview.ex.WorkflowExistedException;
import com.interview.model.Job;
import com.interview.model.JobStatus;
import com.interview.model.Workflow;
import com.interview.service.WorkflowService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 4:27 PM
 */
@SpringBootTest(
     classes = {
         ExecutorConfig.class,
         PersistenceConfig.class,
         ServiceConfig.class
     }
)
@RunWith(SpringRunner.class)
public class WorkflowServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowServiceTest.class);

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private InMemJobStore inMemJobStore;
    @Autowired
    private InMemWorkflowStore inMemWorkflowStore;
    @Autowired
    private InMemJobStatusStore inMemJobStatusStore;

    private static final String GOOD_WF = "goodWork",
        BAD_WF = "badWork",
        JOB_1 = "job1",
        JOB_2 = "job2",
        JOB_3 = "job3";

    @Before
    public void before() {
        inMemJobStore.clear();
        inMemWorkflowStore.clear();
    }

    @Test
    public void testCreateWorkflow() {
        Workflow wf = workflowService.createWorkflow(GOOD_WF);
        assertNotNull(wf);
        assertEquals(GOOD_WF, wf.getName());
    }

    @Test(expected = WorkflowExistedException.class)
    public void testCreateExistedWorkflow() {
        Workflow wf = workflowService.createWorkflow(GOOD_WF);
        assertNotNull(wf);

        workflowService.createWorkflow(GOOD_WF);
    }

    @Test
    public void testGetWorkflow() {
        Workflow wf1 = workflowService.createWorkflow(GOOD_WF);
        Workflow wf2 = workflowService.getWorkflow(GOOD_WF);

        assertEquals(wf1, wf2);
    }

    @Test
    public void testGetNonExistedWorkflow() {
        Workflow wf = workflowService.getWorkflow(BAD_WF);

        assertNull(wf);
    }

    @Test(expected = NotFoundException.class)
    public void testExecuteNonExistedWorkflow() {
        workflowService.executeWorkflow(BAD_WF);
    }

    @Test
    public void testRegisterJobToWf() throws InterruptedException {
        Workflow wf = workflowService.createWorkflow(GOOD_WF);
        Job job1 = workflowService.getOrCreateJob(JOB_1),
            job2 = workflowService.getOrCreateJob(JOB_2),
            job3 = workflowService.getOrCreateJob(JOB_3);

        workflowService.registerJob(wf, job1);
        workflowService.registerJob(wf, job2, Arrays.asList(job1));
        workflowService.registerJob(wf, job3, Arrays.asList(job1, job2));

        workflowService.executeWorkflow(wf.getName());
    }

    @Test(expected = BadRequestException.class)
    public void testRegisterSameJobAsDependentJob() {
        Workflow wf = workflowService.createWorkflow(BAD_WF);
        Job job1 = workflowService.getOrCreateJob(JOB_1);

        workflowService.registerJob(wf, job1, Arrays.asList(job1));
    }

    @Test
    public void testJobStatusChangedAfterExecution() throws InterruptedException {
        Workflow wf = workflowService.createWorkflow(GOOD_WF);
        Job job1 = workflowService.getOrCreateJob(JOB_1);

        workflowService.registerJob(wf, job1);

        workflowService.executeWorkflow(wf.getName());

        Thread.sleep(500);

        JobStatus jobstatus1 = inMemJobStatusStore.getJobStatus(job1);
        assertEquals(JobStatus.EXECUTED, jobstatus1);
    }

}
