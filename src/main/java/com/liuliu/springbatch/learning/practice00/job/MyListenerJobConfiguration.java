package com.liuliu.springbatch.learning.practice00.job;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.liuliu.springbatch.learning.practice00.listener.MyChunkListener;
import com.liuliu.springbatch.learning.practice01.listener.batch.MyJobListener;


//@Configuration
public class MyListenerJobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	// 监听Job执行
	@Bean
	public Job myListenerJob() {
		return jobBuilderFactory.get("myListenerJob").incrementer(new RunIdIncrementer())
				.start(myListenerStep())
				.listener(new MyJobListener())
				.build();
	}

	private Step myListenerStep() {
		return stepBuilderFactory.get("myListenerStep").<String, String> chunk(2)
				.faultTolerant()
				.listener(new MyChunkListener())
				.reader(reader())
				.writer(writer())
				.build();
	}

	private ItemReader<? extends String> reader() {
		return new ListItemReader<>(Arrays.asList("maozedong", "zhude", "pendehuai", "zhouenlai", "liushaoqi"));
	}

	private ItemWriter<? super String> writer() {
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				for (String item : items) {
					System.out.println("Writing item: " + item);
				}
			}
		};
	}
}
