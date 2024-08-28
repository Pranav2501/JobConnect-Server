package chainsys.pranavraj.jobconnect.controller;

import chainsys.pranavraj.jobconnect.model.JobApplication;
import chainsys.pranavraj.jobconnect.model.JobPost;
import chainsys.pranavraj.jobconnect.service.JobApplicationService;
import chainsys.pranavraj.jobconnect.service.JobPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee Controller", description = "Operations related to employees managing job posts and applications")
public class EmployeeController {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Operation(summary = "Get all job posts", description = "Retrieve a list of all available job posts.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of job posts",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobPost.class))})
    })
    @GetMapping("/jobs")
    public List<JobPost> getAllJobPosts() {
        return jobPostService.getAllJobPosts();
    }

    @Operation(summary = "Get job post by ID", description = "Retrieve a job post by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the job post",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobPost.class))}),
        @ApiResponse(responseCode = "404", description = "Job post not found", content = @Content)
    })
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<JobPost> getJobPostById(@PathVariable String jobId) {
        Optional<JobPost> jobPost = jobPostService.getJobPostById(jobId);
        return jobPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Apply for a job", description = "Submit an application for a specific job post.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Application submitted successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<String> applyForJob(@PathVariable String jobId, @Valid @RequestBody JobApplication jobApplication, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        jobApplication.setJobId(jobId);
        jobApplicationService.addJobApplication(jobApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body("Application submitted successfully.");
    }
    
    @Operation(summary = "Get all job applications", description = "Retrieve a list of all job applications submitted by employees.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of job applications",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JobApplication.class))})
    })
    @GetMapping("/applications")
    public List<JobApplication> getAllJobApplications() {
        return jobApplicationService.getAllJobApplications();
    }
}
