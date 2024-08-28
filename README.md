# Job Connect Application

## Overview

A Spring-based application that provides basic functionalities of job portals like LinkedIn, Indeed, etc. The application is designed to facilitate interactions between employers and employees by offering a platform where job postings can be managed and job applications can be submitted efficiently. Employers can manage their job postings, including the ability to create, delete, and modify listings as per their requirements. Employees can browse through available job postings and apply for positions that match their skills and interests. Admins have the capability to manage user roles and permissions, ensuring that the right level of access is granted to each user type. The application focuses on providing a user-friendly interface with essential features to meet the core needs of job seekers and employers while maintaining secure and effective user management through administrative controls.


## Business Requirements / Key Features:

- Allow employers to add, delete or modify their job postings.
- Allow employees to view and apply for available job postings
- Allow admin to add, delete or modify user permissions.

## Functional Requirements:

1. Job Posting Management (Employer):

- Create Job Postings: Employers can create new job listings by providing necessary details such as job title, description, and qualifications.
- Delete Job Postings: Employers can remove job listings that are no longer active or relevant.
- Modify Job Postings: Employers can update existing job listings to reflect changes in job details.

2. Job Application Management (Job Seeker):

- View Job Listings: Job seekers can browse through a list of available jobs, filtered by various criteria.
- Apply for Jobs: Job seekers can submit applications to the jobs they are interested in directly through the platform.
Admin Management:
- Manage User Roles: Admins can assign or revoke roles such as Employer, Job Seeker, or Admin.
- Modify Permissions: Admins can adjust the permissions associated with different roles, ensuring that users have access to the appropriate features.

## Primary Users / Target Audience:

- Employers who need a simple and effective way to post job openings and manage applicants.
- Job Seekers looking for a platform to find and apply to job opportunities that match their qualifications.
- Administrators responsible for overseeing user management and maintaining system security.

## Database Design:

The Job Board Application’s database schema is designed to support the core functionalities of job postings, user management, and job applications. The schema consists of three main tables:

1. job_posts: This table stores information about job listings created by employers. Key fields include:

- job_id: A unique identifier for each job post.
- job_title: The title of the job.
- job_description: A detailed description of the job responsibilities and requirements.
- salary: The offered salary for the position.
- user_id: The identifier for the employer who created the job post.
- application_deadline: The last date for applying to the job.
- date_posted: The date when the job was posted.

2. users: This table manages user data, including both employers and job seekers. Key fields include:

- user_id: A unique identifier for each user.
- username: The name used by the user to log in.
- password: The user's password (stored securely).
- email: The user's email address.
- role: The role of the user, which can be either 'Employer', 'Job Seeker', or 'Admin'.



3. job_applications: This table tracks job applications submitted by job seekers. Key fields include:

- application_id: A unique identifier for each job application.
- job_id: A reference to the job being applied to, linked to the job_posts table.
- user_id: A reference to the job seeker applying for the job, linked to the users table.
- resume_path: The file path where the applicant’s resume is stored.
- applied_date: The date when the application was submitted.



## License

This is licensed under MIT, free-to-use for all.




