package com.liuliu.springbatch.learning.practice00.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FlowDecisionDemoConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job FlowDecisionJob() {
		return jobBuilderFactory.get("FlowDecisionJob").incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(myDecider())
				.from(myDecider()).on("EVEN").to(evenStep())
				.from(myDecider()).on("ODD").to(oddStep())
				.from(oddStep()).on("*").to(myDecider()) 
				.end()
				.build();
		
		//Hello firstStep..
		//Hello oddStep..
		//Hello evenStep..
	}

	@Bean
	public Step firstStep() {
		return stepBuilderFactory.get("firstStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello firstStep..");
				return RepeatStatus.FINISHED;
			}
		}).build();

	}

	@Bean
	public Step evenStep() {
		return stepBuilderFactory.get("evenStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello evenStep..");
				return RepeatStatus.FINISHED;
			}
		}).build();

	}

	@Bean
	public Step oddStep() {
		return stepBuilderFactory.get("oddStep").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello oddStep..");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public JobExecutionDecider myDecider() {
		return new MyDecider();
	}

}