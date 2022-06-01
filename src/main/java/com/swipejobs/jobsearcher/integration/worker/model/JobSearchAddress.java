package com.swipejobs.jobsearcher.integration.worker.model;

import lombok.Data;

@Data
public class JobSearchAddress {

    private String longitude;

    private String latitude;

    private String unit;

    private int maxJobDistance;
}
