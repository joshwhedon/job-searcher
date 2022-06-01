package com.swipejobs.jobsearcher.service;

import com.swipejobs.jobsearcher.cache.WorkerJobCache;
import com.swipejobs.jobsearcher.helper.JobFilteringHelper;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.WorkerJobParameters;
import com.swipejobs.jobsearcher.model.api.WorkerJob;
import com.swipejobs.jobsearcher.model.api.WorkerJobs;
import com.swipejobs.jobsearcher.model.exception.InactiveUserException;
import com.swipejobs.jobsearcher.model.exception.NotFoundUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerJobService {

    @Autowired
    private WorkerJobCache workerJobCache;

    @Autowired
    private JobService jobService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private JobFilteringHelper jobFilteringHelper;

    public WorkerJobs getJobs(WorkerJobParameters parameters) {

        Optional<List<WorkerJob>> optionalWorkerJobs = workerJobCache.getJobs(parameters.getWorkerId());

        if (optionalWorkerJobs.isPresent()) {
            return WorkerJobs
                    .builder()
                    .jobs(jobFilteringHelper.limitJobs(optionalWorkerJobs.get()))
                    .build();
        } else {
            return WorkerJobs
                    .builder()
                    .jobs(getAndProcessWorkerJobs(parameters))
                    .build();
        }
    }

    private List<WorkerJob> getAndProcessWorkerJobs(WorkerJobParameters parameters) {
        Optional<Worker> optionalWorker = workerService.getWorker(parameters);
        if (!optionalWorker.isPresent()) {
            throw new NotFoundUserException("Worker "+ parameters.getWorkerId() + " not found");
        }
        Worker worker = optionalWorker.get();
        if (!worker.isActive()) {
            throw new InactiveUserException("Worker "+ parameters.getWorkerId() + " not active");
        }
        List<Job> jobs = jobService.getJobs();

        List<WorkerJob> workerJobs = jobFilteringHelper.filterJobsForWorker(jobs, worker);
        // We cache the full list, because jobs might become unavailable and we don't want to refilter all the jobs again
        workerJobCache.cacheJobs(parameters.getWorkerId(), workerJobs);
        return jobFilteringHelper.limitJobs(workerJobs);
    }
}
