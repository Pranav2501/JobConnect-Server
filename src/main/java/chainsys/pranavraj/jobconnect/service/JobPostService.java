package chainsys.pranavraj.jobconnect.service;

import chainsys.pranavraj.jobconnect.model.JobPost;
import chainsys.pranavraj.jobconnect.repository.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public Optional<JobPost> getJobPostById(String jobId) {
        return jobPostRepository.findById(jobId);
    }

    public void addJobPost(JobPost jobPost) {
        jobPostRepository.save(jobPost);
    }

    public void updateJobPost(JobPost jobPost) {
        jobPostRepository.save(jobPost);
    }

    public void deleteJobPost(String jobId) {
        jobPostRepository.deleteById(jobId);
    }
}
