package com.liuliu.springbatch.learning.practice00.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class JobFlowDemoTwoConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	// 创建一个Job执行Flow以及Step
	@Bean
	public Job jobFlowDemoTwoJob() {
		return jobBuilderFactory.get("jobFlowDemoTwoJob").incrementer(new RunIdIncrementer())
				.start(jobFlowDemoFlow())
				.next(jobFlowDemoTwo3())
				.end()
				.build();
	}
	
	// 创建Flow,它是一个Step集合
	@Bean
	public Flow jobFlowDemoFlow() {
		return new FlowBuilder<Flow>("jobFlowDemoFlow")
					.start(jobFlowDemoTwo1())
					.next(jobFlowDemoTwo2())
					.build();
	}
	
	@Bean
	public Step jobFlowDemoTwo1() {
		return stepBuilderFactory.get("jobFlowDemoTwo1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello-->jobFlowDemoTwo1");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step jobFlowDemoTwo2() {
		return stepBuilderFactory.get("jobFlowDemoTwo2").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello-->jobFlowDemoTwo2");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step jobFlowDemoTwo3() {
		return stepBuilderFactory.get("jobFlowDemoTwo3").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello-->jobFlowDemoTwo3");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}


}
