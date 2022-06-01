package com.swipejobs.jobsearcher.service;

import com.swipejobs.jobsearcher.TestDataHelper;
import com.swipejobs.jobsearcher.cache.WorkerJobCache;
import com.swipejobs.jobsearcher.helper.JobFilteringHelper;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.WorkerJobParameters;
import com.swipejobs.jobsearcher.model.api.WorkerJob;
import com.swipejobs.jobsearcher.model.api.WorkerJobs;
import com.swipejobs.jobsearcher.model.exception.InactiveUserException;
import com.swipejobs.jobsearcher.model.exception.NotFoundUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {WorkerJobService.class})
@ExtendWith(SpringExtension.class)
public class WorkerJobServiceTest {

    @Autowired
    private WorkerJobService service;

    @MockBean
    private WorkerJobCache workerJobCache;

    @MockBean
    private JobService jobService;

    @MockBean
    private WorkerService workerService;

    @MockBean
    private JobFilteringHelper jobFilteringHelper;

    @Test
    public void getJobs() {
        WorkerJobParameters parameters = WorkerJobParameters.builder().workerId("1").build();
        List<WorkerJob> jobs = Collections.emptyList();
        when(workerJobCache.getJobs(eq("1"))).thenReturn(Optional.of(jobs));

        WorkerJobs workerJobs = service.getJobs(parameters);
        assertEquals(jobs, workerJobs.getJobs());

        when(workerJobCache.getJobs(eq("1"))).thenReturn(Optional.empty());
        when(workerService.getWorker(eq(parameters))).thenReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, () -> {
            service.getJobs(parameters);
        });

        Worker worker = TestDataHelper.buildWorker("1");
        worker.setActive(false);
        when(workerService.getWorker(eq(parameters))).thenReturn(Optional.of(worker));

        assertThrows(InactiveUserException.class, () -> {
            service.getJobs(parameters);
        });

        List<Job> availableJobs = TestDataHelper.buildJobs();

        when(jobService.getJobs()).thenReturn(availableJobs);

        worker.setActive(true);

        when(jobFilteringHelper.filterJobsForWorker(eq(availableJobs), eq(worker))).thenReturn(jobs);

        workerJobs = service.getJobs(parameters);
        assertEquals(jobs, workerJobs.getJobs());
    }
}
