package chainsys.pranavraj.jobconnect.service;

import chainsys.pranavraj.jobconnect.model.JobPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobPostService {

    private List<JobPost> jobPosts;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath = "src/main/resources/JobPosts.json";  // Path to JSON file

    public JobPostService() {
        this.jobPosts = loadJobPostsFromFile();
    }

    public List<JobPost> getAllJobPosts() {
        return jobPosts;
    }

    public Optional<JobPost> getJobPostById(String jobId) {
        return jobPosts.stream().filter(job -> job.getJobId().equals(jobId)).findFirst();
    }

    public void addJobPost(JobPost jobPost) {
        jobPosts.add(jobPost);
        saveJobPostsToFile();
    }

    public void updateJobPost(JobPost jobPost) {
        jobPosts.removeIf(existingJob -> existingJob.getJobId().equals(jobPost.getJobId()));
        jobPosts.add(jobPost);
        saveJobPostsToFile();
    }

    public void deleteJobPost(String jobId) {
        jobPosts.removeIf(job -> job.getJobId().equals(jobId));
        saveJobPostsToFile();
    }

    private List<JobPost> loadJobPostsFromFile() {
        try (InputStream inputStream = getClass().getResourceAsStream("/JobPosts.json")) {
            return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, JobPost.class));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveJobPostsToFile() {
        try {
            File file = new File(filePath);
            objectMapper.writeValue(file, jobPosts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
