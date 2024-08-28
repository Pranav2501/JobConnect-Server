package chainsys.pranavraj.jobconnect.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobposts")
public class JobPost {

    private String jobId;

    @NotEmpty(message = "Job title is required")
    @Size(min = 3, max = 100, message = "Job title must be between 3 and 100 characters")
    private String jobTitle;

    @NotEmpty(message = "Job description is required")
    @Size(min = 10, max = 1000, message = "Job description must be between 10 and 1000 characters")
    private String jobDescription;

    @NotEmpty(message = "Salary is required")
    private String salary;

    @NotEmpty(message = "Employer ID is required")
    private String employerId;

    @NotEmpty(message = "Application deadline is required")
    private String applicationDeadline;

    // Getters and Setters
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }
}
