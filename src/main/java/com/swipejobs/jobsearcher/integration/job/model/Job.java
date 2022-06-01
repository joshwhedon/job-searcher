package com.swipejobs.jobsearcher.integration.job.model;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class Job {

    private String guid;

    private String jobId;

    private boolean driverLicenseRequired;

    private int workersRequired;

    private List<String> requiredCertificates;

    private String company;

    private String about;

    private String jobTitle;

    private String billRate;

    private ZonedDateTime startDate;

    private Location location;
}
