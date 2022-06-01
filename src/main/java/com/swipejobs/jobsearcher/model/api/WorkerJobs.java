package com.swipejobs.jobsearcher.model.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@Schema(description = "Represents jobs available to a worker.")
public class WorkerJobs {

    @Builder.Default
    @Schema(description = "The list of jobs available to the worker")
    private List<WorkerJob> jobs = Collections.emptyList();
}
