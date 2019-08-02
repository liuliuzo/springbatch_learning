package com.liuliu.springbatch.learning.practice00.job;

import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class JobParametersConfiguretion implements StepExecutionListener {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	private Map<String, JobParameter> parames;

	@Bean
	public Job MyParametersJobThree() {
		return jobBuilderFactory.get("MyParametersJobThree").incrementer(new RunIdIncrementer())
				.start(MyParametersJobStep3())
				.build();
	}

	private Step MyParametersJobStep3() {
		return stepBuilderFactory.get("MyParametersJobStep3").listener(this).tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("parame is: " + parames.get("info"));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println(stepExecution.getStepName() + "运行之前...........");
		parames = stepExecution.getJobParameters().getParameters();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println(stepExecution.getStepName() + "运行之完毕...........");
		return null;
	}
}
