package com.liuliu.springbatch.learning.practice00.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemProcessor;
import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemReader;
import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemWriter;
import com.liuliu.springbatch.learning.practice01.listener.batch.MyJobListener;
import com.liuliu.springbatch.learning.practice01.model.Person;

/**
 * Multi-threaded Step (single process)
 */
//@Configuration
public class MultiThreadConfiguration {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
    @Bean
    public JobExecutionListener myJobListener() {
        return new MyJobListener();
    }
	
    @Bean
    public Job multiThreadSingleProcessJob(Step sampleStep) {
        return jobBuilderFactory.get("multiThreadSingleProcessJob")
                .incrementer(new RunIdIncrementer())
                .flow(sampleStep)
                .end()
                .listener(myJobListener())
                .build();
    }
	
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

	@Bean
	public Step sampleStep(TaskExecutor taskExecutor, ItemReader<Person> reader, ItemWriter<Person> writer,
			ItemProcessor<Person, Person> processor) {
		return this.stepBuilderFactory.get("sampleStep").<Person, Person> chunk(2)
				                      .reader(reader)
				                      .writer(writer)
				                      .processor(processor)
				                      .taskExecutor(taskExecutor)
				                      .throttleLimit(2)//throttleLimit maximum number of concurrent tasklet executions allowed
				                      .build();
	}
	
	//reader,processor,writer三兄弟
    @Bean
    public ItemReader<Person> reader() {
    	List<Person> list=new ArrayList<>();
    	list.add(new Person("name1","age1"));
    	list.add(new Person("name2","age2"));
    	list.add(new Person("name3","age3"));
    	list.add(new Person("name4","age4"));
    	list.add(new Person("name5","age5"));
    	list.add(new Person("name6","age6"));
    	list.add(new Person("name7","age7"));
    	list.add(new Person("name8","age8"));
    	list.add(new Person("name9","age9"));
    	list.add(new Person("name10","age10"));
    	list.add(new Person("name11","age11"));
    	list.add(new Person("name12","age12"));
        return new SimpleItemReader(list);
    }
    @Bean
    public ItemWriter<Person> writer() {
        return new SimpleItemWriter();
    }
    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new SimpleItemProcessor();
    }
}
