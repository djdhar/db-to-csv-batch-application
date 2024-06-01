package com.batchapp.dbcsv.controller;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  org.springframework.batch.core.Job;

@RestController
@RequestMapping("/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job exportedUserJob;

    public BatchController(JobLauncher jobLauncher, Job exportedUserJob) {
        this.jobLauncher = jobLauncher;
        this.exportedUserJob = exportedUserJob;
    }

    @GetMapping("/run")
    public String runBatchJob() {
        try {
            jobLauncher.run(exportedUserJob, new JobParametersBuilder().toJobParameters());
            return "Batch job has been invoked";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to run batch job";
        }
    }
}
