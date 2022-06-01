package com.swipejobs.jobsearcher.helper;

import com.swipejobs.jobsearcher.TestDataHelper;
import com.swipejobs.jobsearcher.configuration.BusinessConfiguration;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import com.swipejobs.jobsearcher.integration.job.model.Location;
import com.swipejobs.jobsearcher.integration.worker.model.JobSearchAddress;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.api.WorkerJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {JobFilteringHelper.class})
@ExtendWith(SpringExtension.class)
public class JobFilteringHelperTest {

    @Autowired
    private JobFilteringHelper helper;

    @MockBean
    private BusinessConfiguration businessConfiguration;

    @Test
    public void limitJobs() {
        when(businessConfiguration.getMaxJobs()).thenReturn(1);
        List<WorkerJob> jobs = helper.limitJobs(TestDataHelper.buildWorkerJobs());
        assertEquals(1, jobs.size());
    }

    @Test
    public void filterJobsForWorker() {
        Worker worker = TestDataHelper.buildWorker("0");
        List<Job> jobs = TestDataHelper.buildJobs();

        List<WorkerJob> workerJobs = helper.filterJobsForWorker(jobs, worker);
        assertEquals(1, workerJobs.size());
        assertEquals("2", workerJobs.get(0).getId());

        worker.setHasDriverLicense(true);
        workerJobs = helper.filterJobsForWorker(jobs, worker);
        assertEquals(3, workerJobs.size());
        assertEquals("1", workerJobs.get(0).getId());
        assertEquals("2", workerJobs.get(1).getId());
        assertEquals("3", workerJobs.get(2).getId());
    }

    @Test
    public void checkDistance() {
        Location location = new Location();
        location.setLatitude("49.994037");
        location.setLongitude("14.013835");
        JobSearchAddress jobSearchAddress = new JobSearchAddress();
        jobSearchAddress.setLongitude("13.971284");
        jobSearchAddress.setLatitude("49.782281");
        jobSearchAddress.setUnit("km");
        jobSearchAddress.setMaxJobDistance(50);

        assertEquals(true, helper.checkDistance(location, jobSearchAddress));
        jobSearchAddress.setMaxJobDistance(1);
        assertEquals(false, helper.checkDistance(location, jobSearchAddress));

        jobSearchAddress.setUnit("test");
        assertEquals(false, helper.checkDistance(location, jobSearchAddress));

        jobSearchAddress.setUnit("km");
        jobSearchAddress.setLatitude("test");
        assertEquals(false, helper.checkDistance(location, jobSearchAddress));

    }
}
