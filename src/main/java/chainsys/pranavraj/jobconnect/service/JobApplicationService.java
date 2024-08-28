package chainsys.pranavraj.jobconnect.service;

import chainsys.pranavraj.jobconnect.model.JobApplication;
import chainsys.pranavraj.jobconnect.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    public void addJobApplication(JobApplication jobApplication) {
        jobApplicationRepository.save(jobApplication);
    }

    public void deleteJobApplication(String applicationId) {
        jobApplicationRepository.deleteById(applicationId);
    }
}
