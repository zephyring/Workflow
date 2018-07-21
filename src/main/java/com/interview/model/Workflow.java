package com.interview.model;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:22 PM
 */
@Data
public class Workflow {
    private String name;
    private Set<Job> jobs = new LinkedHashSet<>();

    public Workflow(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Workflow workflow = (Workflow) o;

        return name.equals(workflow.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
