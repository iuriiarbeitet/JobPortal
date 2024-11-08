package com.dev.jobportal.service;

import com.dev.jobportal.entity.*;
import com.dev.jobportal.entity.mapper.RecruiterJobsDto;
import com.dev.jobportal.repository.JobPostActivityRepository;
import com.dev.jobportal.repository.JobSeekerApplyRepository;
import com.dev.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;
    private final JobSeekerSaveRepository jobSeekerSaveRepository;
    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository, JobSeekerSaveRepository jobSeekerSaveRepository, JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }
    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {

        List<IRecruiterJobs> recruiterJobsDto = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for (IRecruiterJobs rec : recruiterJobsDto) {
            JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
            JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(),
                    rec.getJob_title(), loc, comp));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {
        return Objects.isNull(searchDate)?jobPostActivityRepository.searchWithoutDate(job, location, remote, type):
                jobPostActivityRepository.search(job, location, remote, type, searchDate);
    }

    public void deleteJob(JobPostActivity jobPostActivity) {

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveRepository.findByJob(jobPostActivity);
        if (!jobSeekerSaveList.isEmpty()) {
            JobSeekerSave jobSeekerSave = jobSeekerSaveList.getFirst();
            jobSeekerSaveRepository.delete(jobSeekerSave);
        }
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyRepository.findByJob(jobPostActivity);
        if (!jobSeekerSaveList.isEmpty()) {
            JobSeekerApply jobSeekerApply = jobSeekerApplyList.getFirst();
            jobSeekerApplyRepository.delete(jobSeekerApply);
        }
        jobPostActivityRepository.delete(jobPostActivity);

    }
}
