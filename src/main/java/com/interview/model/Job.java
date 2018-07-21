package com.interview.model;

import java.util.List;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:18 PM
 */
public interface Job {
    String getName();
    void registerDependencies(List<Job> jobs);
    void execute();
    JobStatus getJobStatus();
    void setJobStatus(JobStatus jobStatus);
}
