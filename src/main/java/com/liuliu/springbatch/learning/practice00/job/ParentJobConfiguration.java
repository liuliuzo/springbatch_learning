package com.liuliu.springbatch.learning.practice00.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


//@Configuration
public class ParentJobConfiguration {
	
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private Job childJob1;
    
    @Autowired
    private Job childJob2;
    
    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job parentJob(JobRepository repository,PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("parentJob")
                .start(parentJobStep())
                .next(childJob1(repository, transactionManager))
                .next(childJob2(repository, transactionManager))
                .build();
    }

    private Step childJob1(JobRepository repository,PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(childJob1)
                .launcher(jobLauncher)
                .repository(repository)
                .transactionManager(transactionManager)
                .build();
    }

    private Step childJob2(JobRepository repository,PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(childJob2)
                .launcher(jobLauncher)
                .repository(repository)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step parentJobStep() {
        return stepBuilderFactory.get("parentJobStep").tasklet(new Tasklet() {
        	@Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
               System.out.println("Helllo------>parentJobStep..");
               return RepeatStatus.FINISHED;
           }}).build();
    }
}