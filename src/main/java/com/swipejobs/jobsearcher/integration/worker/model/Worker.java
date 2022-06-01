package com.swipejobs.jobsearcher.integration.worker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Worker {

    private String guid;

    private String userId;

    @JsonProperty("isActive")
    private boolean isActive;

    private List<String> certificates;

    private JobSearchAddress jobSearchAddress;

    private boolean hasDriverLicense;
}
