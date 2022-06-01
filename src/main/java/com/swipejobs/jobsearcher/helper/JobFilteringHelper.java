package com.swipejobs.jobsearcher.helper;

import com.swipejobs.jobsearcher.configuration.BusinessConfiguration;
import com.swipejobs.jobsearcher.integration.job.model.Job;
import com.swipejobs.jobsearcher.integration.job.model.Location;
import com.swipejobs.jobsearcher.integration.worker.model.JobSearchAddress;
import com.swipejobs.jobsearcher.integration.worker.model.Worker;
import com.swipejobs.jobsearcher.model.api.WorkerJob;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobFilteringHelper {

    private static final String UNIT_KILOMETER = "km";
    private static final String UNIT_METER = "m";

    @Autowired
    private BusinessConfiguration businessConfiguration;

    private GeodeticCalculator geodeticCalculator = new GeodeticCalculator();

    public List<WorkerJob> filterJobsForWorker(List<Job> jobs, Worker worker) {
        return jobs
                .parallelStream()
                .filter(job -> job.isDriverLicenseRequired() ? worker.isHasDriverLicense() : true)
                .filter(job -> job.getRequiredCertificates().isEmpty() || worker.getCertificates().containsAll(job.getRequiredCertificates()))
                .filter(job -> checkDistance(job.getLocation(), worker.getJobSearchAddress()))
                .map(job -> new WorkerJob(job))
                .collect(Collectors.toList());
    }

    // For now, we just limit without ordering, but in an ideal world, we would probably try to fill jobs which have the smaller amount of workers required or some other ordering rules
    public List<WorkerJob> limitJobs(List<WorkerJob> jobs) {
        return jobs
                .stream()
                .limit(businessConfiguration.getMaxJobs())
                .collect(Collectors.toList());
    }

    protected boolean checkDistance(Location location, JobSearchAddress jobSearchAddress) {
        try {
            double distance = distance(location.getLatitude(), location.getLongitude(), jobSearchAddress.getLatitude(), jobSearchAddress.getLongitude()) * getUnitMultiplier(jobSearchAddress.getUnit());
            return distance <= jobSearchAddress.getMaxJobDistance();
        } catch (NumberFormatException exception) {
            // Can this really happen, and what should we do when this happen ? For now assume the job or worker data is invalid, and move on
            exception.printStackTrace();
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    // Need to find out all possible units
    protected double getUnitMultiplier(String unit) throws Exception {
        switch (unit) {
            case UNIT_KILOMETER:
                return 0.001;
            case UNIT_METER:
                return 1;
        }
        throw new Exception("Unknown unit " + unit);
    }

    private double distance(String lat1, String lon1, String lat2, String lon2) {
        return distance(Double.parseDouble(lat1), Double.parseDouble(lon1), Double.parseDouble(lat2), Double.parseDouble(lon2));
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // select a reference elllipsoid
        Ellipsoid reference = Ellipsoid.WGS84;

        GlobalCoordinates location1 = new GlobalCoordinates(lat1, lon1);
        GlobalCoordinates location2 = new GlobalCoordinates(lat2, lon2);

        // calculate the geodetic curve
        GeodeticCurve geoCurve = geodeticCalculator.calculateGeodeticCurve(reference, location1, location2);
        return geoCurve.getEllipsoidalDistance();
    }
}
