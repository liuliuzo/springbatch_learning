package com.liuliu.springbatch.learning;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringbatchLearningApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchLearningApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("date", new Date()); 
		//jobLauncher.run(job, builder.toJobParameters());
	}

}
