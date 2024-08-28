package chainsys.pranavraj.jobconnect.repository;

import chainsys.pranavraj.jobconnect.model.JobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends MongoRepository<JobApplication, String> {
}
