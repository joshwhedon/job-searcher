package com.swipejobs.jobsearcher.service;

import com.swipejobs.jobsearcher.TestDataHelper;
import com.swipejobs.jobsearcher.cache.WorkerCache;
import com.swipejobs.jobsearcher.integration.worker.WorkerWebService;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.WorkerParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {WorkerService.class})
@ExtendWith(SpringExtension.class)
public class WorkerServiceTest {

    @Autowired
    private WorkerService service;

    @MockBean
    private WorkerCache workerCache;

    @MockBean
    private WorkerWebService workerWebService;

    @Test
    public void getWorker() {
        Worker worker = TestDataHelper.buildWorker("1");
        when(workerCache.getWorker(eq("1"))).thenReturn(Optional.of(worker));
        assertEquals(worker, service.getWorker(WorkerParameters.builder().workerId("1").build()).get());

        when(workerCache.getWorker(anyString())).thenReturn(Optional.empty());
        when(workerWebService.getWorkers()).thenReturn(Flux.just(TestDataHelper.buildWorker("2"), worker));

        assertEquals(worker, service.getWorker(WorkerParameters.builder().workerId("1").build()).get());
    }
}
