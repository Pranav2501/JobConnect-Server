package chainsys.pranavraj.jobconnect.repository;

import chainsys.pranavraj.jobconnect.model.JobPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends MongoRepository<JobPost, String> {
}
