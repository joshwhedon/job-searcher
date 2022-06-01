package com.swipejobs.jobsearcher;

import com.swipejobs.jobsearcher.integration.job.model.Job;
import com.swipejobs.jobsearcher.integration.job.model.Location;
import com.swipejobs.jobsearcher.integration.worker.model.JobSearchAddress;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.api.WorkerJob;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestDataHelper {

    public static List<Job> buildJobs() {
        List<Job> jobs = new ArrayList<>();
        Job job = buildJob("0");
        jobs.add(job);

        job = buildJob("1");
        job.setRequiredCertificates(Arrays.asList("Cert1"));
        jobs.add(job);

        job = buildJob("2");
        job.setRequiredCertificates(Arrays.asList("Cert1"));
        job.setDriverLicenseRequired(false);
        jobs.add(job);

        job = buildJob("3");
        job.setRequiredCertificates(Collections.emptyList());
        jobs.add(job);

        job = buildJob("4");
        job.setJobId("4");
        job.getLocation().setLatitude("0");
        job.getLocation().setLongitude("0");
        job.setRequiredCertificates(new ArrayList<>());
        job.setDriverLicenseRequired(false);
        jobs.add(job);
        return jobs;
    }

    public static Job buildJob(String id) {
        Job job = new Job();
        job.setJobId(id);
        job.setLocation(new Location());
        job.getLocation().setLatitude("49.994037");
        job.getLocation().setLongitude("14.013835");
        job.setWorkersRequired(1);
        job.setRequiredCertificates(Arrays.asList("Cert1", "Cert2", "Cert3"));
        job.setDriverLicenseRequired(true);
        return job;
    }

    public static List<WorkerJob> buildWorkerJobs() {
        return Arrays.asList(new WorkerJob(new Job()), new WorkerJob(new Job()), new WorkerJob(new Job()));
    }

    public static Worker buildWorker(String id) {
        Worker worker = new Worker();
        worker.setUserId(id);
        worker.setActive(true);
        worker.setCertificates(Arrays.asList("Cert1"));
        worker.setJobSearchAddress(new JobSearchAddress());
        worker.getJobSearchAddress().setLongitude("13.971284");
        worker.getJobSearchAddress().setLatitude("49.782281");
        worker.getJobSearchAddress().setMaxJobDistance(50);
        worker.getJobSearchAddress().setUnit("km");
        return worker;
    }
}
