package com.interview.model;

import com.interview.db.InMemJobStatusStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:19 PM
 */
public class SimplePrintNameJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(SimplePrintNameJob.class);

    private String name;
    private Set<Job> dependencies = new LinkedHashSet<>();
    private InMemJobStatusStore jobStatusStore;

    public SimplePrintNameJob() {
    }

    public SimplePrintNameJob(String name, InMemJobStatusStore jobStatusStore) {
        this.name = name;
        this.jobStatusStore = jobStatusStore;
    }

    public String getName() {
        return name;
    }

    public void registerDependencies(List<Job> jobs) {
        dependencies.addAll(jobs);
    }

    public void execute() {
        dependencies.forEach(Job::execute);

        JobStatus status = getJobStatus();

        // this job may have been executed by other jobs as a dependency
        // just skip
        if (JobStatus.EXECUTED.equals(status) ||
            JobStatus.RUNNING.equals(status)) {
            return;
        }

        setJobStatus(JobStatus.RUNNING);

        LOG.info("Executing Job {}", name);

        setJobStatus(JobStatus.EXECUTED);
    }

    @Override
    public JobStatus getJobStatus() {
        return jobStatusStore.getJobStatus(this);
    }

    @Override
    public void setJobStatus(JobStatus jobStatus) {
        jobStatusStore.updateJobStatus(this, jobStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplePrintNameJob that = (SimplePrintNameJob) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
