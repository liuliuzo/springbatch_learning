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
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Parallel Steps (single process)
 * 
 */
//@Configuration
public class JobSpiltDemoConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	/**
	 * 创建job运行Flow,我们利用split(new SimpleAsyncTaskExecutor()).add()让flow异步执行,add()中可以添加多个Flow
	 * 
	 * @return
	 */
	@Bean
	public Job SpiltJob() {
		return jobBuilderFactory.get("SpiltJob").incrementer(new RunIdIncrementer())
				.start(jobSpiltFlow1())
				.split(new SimpleAsyncTaskExecutor())
				.add(jobSpiltFlow2())
				.next(jobSpiltStep10())
				.end()
				.build();
	}

	// 创建Flow1
	public Flow jobSpiltFlow1() {
		return new FlowBuilder<Flow>("jobSpiltFlow1")
				.start(stepBuilderFactory.get("jobSpiltStep1").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep2").tasklet(tasklet()).build()).build();

	}

	// 创建Flow1
	public Flow jobSpiltFlow2() {
		return new FlowBuilder<Flow>("jobSpiltFlow2")
				.start(stepBuilderFactory.get("jobSpiltStep3").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep4").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep5").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep6").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep7").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep8").tasklet(tasklet()).build())
				.next(stepBuilderFactory.get("jobSpiltStep9").tasklet(tasklet()).build())
				.build();
	}

	// 最后一个执行
	public Step jobSpiltStep10() {
		return stepBuilderFactory.get("jobSpiltStep10").tasklet(tasklet()).build();
	}

	private Tasklet tasklet() {
		return new PrintTasklet();
	}

	// step执行的任务类（可以写为外部类，此处为了方便，我们写为内部类）
	private class PrintTasklet implements Tasklet {
		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			System.out.println("has been execute on stepName:" + chunkContext.getStepContext().getStepName()
					+ ",has been execute on thread:" + Thread.currentThread().getName());
			return RepeatStatus.FINISHED;
		}
	}

}
