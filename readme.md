Notes
- Was built with java 15
- Most of the packages should be moved to libraries to allow them to be reused.
- Documentation could be improved (particularly around possible response codes)
- Only in memory cache has been built and tested for now, but service has been implemented with the possibility to have different cache implementation in place, particularly for local development. Mongo implementation has been prepared but due to a lack of time, experience, and an older version of java currently installed (using it for other projects), I was not able to complete it.
- Unit testing have only been written around the "priority" classes, but should ideally cover 90% of the classes.
- Job cache update is not handled, service should listen to job changes and remove job from cache when workers required drop to 0 (both the job cache and worker cache), and updated when job changes (however it is unknown at the moment what should happen to the worker job cache in this case)
- Worker cache update is not handled, while we handle the fact that new workers might be added, we don't handle updates to the worker itself
- Populating the job cache is not ideal on start up, as it could take take for the service to become available, and would not be needed in a real situation as the cache would already be populated.
- Thread safety and cache versioning hasn't really been looked at.

Assumptions
- Fields provided by the API are mandatory and a contract has been agreed upon (if they are not mandatory, fields should be wrapped in optionals)
- userId and jobId are the consumer friendly ids, guid is used to identify unique jobs and versioning, therefore we are only going to care about userId and jobId for this exercise
- requiredCertificates and certificates are not free text, and therefore matching does not need to be fuzzy. If they are free text, spelling mistakes and other mismatches would need to be taken into consideration in the matching algorithm
- While it is assumed that a job with workersRequired at 0 would not be returned by the api, the job is still filtered out.