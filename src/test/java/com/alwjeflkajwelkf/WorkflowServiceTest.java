package com.alwjeflkajwelkf;

import com.alwjeflkajwelkf.config.ExecutorConfig;
import com.alwjeflkajwelkf.config.PersistenceConfig;
import com.alwjeflkajwelkf.config.ServiceConfig;
import com.alwjeflkajwelkf.db.InMemJobStatusStore;
import com.alwjeflkajwelkf.db.InMemJobStore;
import com.alwjeflkajwelkf.db.InMemWorkflowStore;
import com.alwjeflkajwelkf.ex.BadRequestException;
import com.alwjeflkajwelkf.ex.NotFoundException;
import com.alwjeflkajwelkf.ex.WorkflowExistedException;
import com.alwjeflkajwelkf.model.Job;
import com.alwjeflkajwelkf.model.JobStatus;
import com.alwjeflkajwelkf.model.Workflow;
import com.alwjeflkajwelkf.service.WorkflowService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        JOB_3 = "job3",
        CONCURR_JOB = "concurrJob";

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

    @Test
    public void stressTestWorkflowExecution() throws InterruptedException {
        int maxWfCnt = 1000,
            eachJobCnt = 5;

        List<Workflow> wfs = new LinkedList<>();

        for (int i = 0; i < maxWfCnt; i++) {
            Workflow wf = workflowService.createWorkflow(GOOD_WF + i);
            for (int j = 0; j < eachJobCnt; j++) {
                workflowService.registerJob(wf, workflowService.getOrCreateJob(CONCURR_JOB + i + "_" + j));
            }
            wfs.add(wf);
        }

        wfs.forEach(w -> workflowService.executeWorkflow(w.getName()));

        Thread.sleep(1000);
    }

}
