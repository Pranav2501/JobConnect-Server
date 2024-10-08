package chainsys.pranavraj.jobconnect.controller;

import chainsys.pranavraj.jobconnect.model.JobPost;
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
@RequestMapping("/employer")
@Tag(name = "Employer Controller", description = "Operations related to employers managing job posts")
public class EmployerController {

    @Autowired
    private JobPostService jobPostService;

    @Operation(summary = "Get all job posts", description = "Retrieve a list of all job posts created by the employer.")
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

    @Operation(summary = "Create a new job post", description = "Create a new job post for the employer.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Job post created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PostMapping("/jobs")
    public ResponseEntity<String> addJobPost(@Valid @RequestBody JobPost jobPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        jobPostService.addJobPost(jobPost);
        return ResponseEntity.status(HttpStatus.CREATED).body("Job post created successfully.");
    }

    @Operation(summary = "Update a job post", description = "Update an existing job post by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job post updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Job post not found", content = @Content)
    })
    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<String> updateJobPost(@PathVariable String jobId, @Valid @RequestBody JobPost jobPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        jobPost.setJobId(jobId);
        jobPostService.updateJobPost(jobPost);
        return ResponseEntity.ok("Job post updated successfully.");
    }

    @Operation(summary = "Delete a job post", description = "Delete an existing job post by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Job post deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Job post not found", content = @Content)
    })
    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<String> deleteJobPost(@PathVariable String jobId) {
        jobPostService.deleteJobPost(jobId);
        return ResponseEntity.ok("Job post deleted successfully.");
    }
}
