package com.swipejobs.jobsearcher.model.api;

import com.swipejobs.jobsearcher.integration.job.model.Job;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Schema(description = "Represents a job available to a worker")
public class WorkerJob {

    public WorkerJob(Job job) {
        this.id = job.getJobId();
        this.description = job.getAbout();
        this.company = job.getCompany();
        this.title = job.getJobTitle();
        this.billRate = job.getBillRate();
        this.startDate = job.getStartDate();
    }

    @Schema(description = "The job unique identifier")
    private String id;

    @Schema(description = "The job description")
    private String description;

    @Schema(description = "The company the jobs is for")
    private String company;

    @Schema(description = "The job title")
    private String title;

    @Schema(description = "The hourly rate of the job, in dollars", example = "$6.16")
    private String billRate;

    @Schema(description = "The job start date", example = "2015-11-12T09:29:19.188Z")
    private ZonedDateTime startDate;
}
