package com.swipejobs.jobsearcher.controller;

import com.swipejobs.jobsearcher.model.WorkerJobParameters;
import com.swipejobs.jobsearcher.model.api.WorkerJobs;
import com.swipejobs.jobsearcher.service.WorkerJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private WorkerJobService workerJobService;

    // Todo: Validate worker id format
    @Operation(summary = "Get the jobs available to a worker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jobs available to a worker",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WorkerJobs.class)) }),
            @ApiResponse(responseCode = "400", description = "Worker not available",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/workers/{workerId}/jobs")
    public WorkerJobs getJobs(@Parameter(description = "Id of the worker searching for jobs") @PathVariable(name = "workerId") String workerId) {
        return workerJobService.getJobs(WorkerJobParameters
                .builder()
                .workerId(workerId)
                .build());
    }
}
