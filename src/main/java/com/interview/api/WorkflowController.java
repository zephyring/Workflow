package com.interview.api;

import com.interview.api.request.CreateJobRequest;
import com.interview.api.request.CreateWorkflowRequest;
import com.interview.ex.BadRequestException;
import com.interview.ex.NotFoundException;
import com.interview.model.Job;
import com.interview.model.Workflow;
import com.interview.service.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:47 PM
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WorkflowController extends BaseRestController {
    private static final Logger LOG = LoggerFactory.getLogger(WorkflowController.class);

    @Autowired
    private WorkflowService workflowService;

    @RequestMapping(value = "/workflow", method = RequestMethod.POST)
    public WorkflowResponse<String> createWorkflow(
        @RequestBody CreateWorkflowRequest request
    ) {
        if (StringUtils.isEmpty(request.getName())) {
            throw new BadRequestException("Name must not be empty");
        }

        workflowService.createWorkflow(request.getName());

        return new WorkflowResponse<>("Workflow created");
    }

    @RequestMapping(value = "/workflow/{name}/jobs", method = RequestMethod.POST)
    public WorkflowResponse<String> createJob(
        @PathVariable("name") String workflowName,
        @RequestBody CreateJobRequest request
    ) {
        if (StringUtils.isEmpty(request.getName())) {
            throw new BadRequestException("Name must not be empty");
        }

        Workflow wf = workflowService.getWorkflow(workflowName);

        if (wf == null) {
            throw new NotFoundException("Workflow " + workflowName + " not found");
        }

        Job job = workflowService.getOrCreateJob(request.getName());

        List<Job> dependencies = request.getDependencies().stream()
            .map(workflowService::getOrCreateJob)
            .collect(Collectors.toList());

        workflowService.registerJob(wf, job, dependencies);

        return new WorkflowResponse<>("Job created for workflow");
    }

    @RequestMapping(value = "/workflow/{name}/execute", method = RequestMethod.POST)
    public WorkflowResponse<String> executeWorkflow(
        @PathVariable("name") String workflowName
    ) {
        workflowService.executeWorkflow(workflowName);

        return new WorkflowResponse<>("Workflow successfully executed");
    }
}
