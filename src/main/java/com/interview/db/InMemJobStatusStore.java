package com.interview.db;

import com.interview.model.Job;
import com.interview.model.JobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 9:01 PM
 */
public class InMemJobStatusStore {
    private static final Logger LOG = LoggerFactory.getLogger(InMemJobStatusStore.class);

    private Map<Job, JobStatus> statusMap = new ConcurrentHashMap<>();

    public JobStatus getJobStatus(Job job) {
        return statusMap.get(job);
    }

    public void updateJobStatus(Job job, JobStatus jobStatus) {
        statusMap.put(job, jobStatus);
    }
}
