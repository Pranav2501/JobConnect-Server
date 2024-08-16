package chainsys.pranavraj.jobconnect.controller;

import chainsys.pranavraj.jobconnect.model.JobPost;
import chainsys.pranavraj.jobconnect.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private JobPostService jobPostService;

    @GetMapping("/jobs")
    public List<JobPost> getAllJobPosts() {
        return jobPostService.getAllJobPosts();
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<JobPost> getJobPostById(@PathVariable String jobId) {
        Optional<JobPost> jobPost = jobPostService.getJobPostById(jobId);
        return jobPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/jobs")
    public ResponseEntity<String> addJobPost(@RequestBody JobPost jobPost) {
        jobPostService.addJobPost(jobPost);
        return ResponseEntity.status(HttpStatus.CREATED).body("Job post created successfully.");
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<String> updateJobPost(@PathVariable String jobId, @RequestBody JobPost jobPost) {
        jobPost.setJobId(jobId);
        jobPostService.updateJobPost(jobPost);
        return ResponseEntity.ok("Job post updated successfully.");
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJobPost(@PathVariable String jobId) {
        jobPostService.deleteJobPost(jobId);
        return ResponseEntity.ok("Job post deleted successfully.");
    }
}
