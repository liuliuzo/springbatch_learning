package com.liuliu.springbatch.learning.practice00.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
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
public class JobFlowDemOne {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job JobFlowDemo1() {
		return jobBuilderFactory.get("JobFlowDemo1").incrementer(new RunIdIncrementer())
				.start(step1())
				.next(step2())
				.next(step3())
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step1-->Hello Spring Batch....");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet((contribution, context) -> {
			System.out.println("step 2-->Hello Spring Batch..");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step3-->Hello Spring Batch....");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
}