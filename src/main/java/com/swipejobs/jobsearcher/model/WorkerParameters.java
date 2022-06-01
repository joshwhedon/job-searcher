package com.swipejobs.jobsearcher.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class WorkerParameters {

    private String workerId;
}
