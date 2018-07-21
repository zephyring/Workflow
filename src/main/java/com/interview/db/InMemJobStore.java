package com.interview.db;

import com.interview.model.Job;
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
public class InMemJobStore {
    private static final Logger LOG = LoggerFactory.getLogger(InMemJobStore.class);

    private Map<String, Job> jobs = new ConcurrentHashMap<>();

    public void putJob(Job job) {
        jobs.put(job.getName(), job);
    }

    public Job getJob(String name) {
        return jobs.get(name);
    }

    public void clear() {
        jobs.clear();
    }
}
