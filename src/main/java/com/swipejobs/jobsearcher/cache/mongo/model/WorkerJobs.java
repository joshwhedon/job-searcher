package com.swipejobs.jobsearcher.cache.mongo.model;

import com.swipejobs.jobsearcher.model.api.WorkerJob;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WorkerJobs {

    private String guid;

    private String userId;

    private List<WorkerJob> jobs;
}
