package com.liuliu.springbatch.learning.practice00.job;

import java.text.SimpleDateFormat;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemProcessor;
import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemWriter;
import com.liuliu.springbatch.learning.practice01.listener.batch.MyJobListener;
import com.liuliu.springbatch.learning.practice01.model.Person;

//@Configuration
public class CommonBatchConfiguration {
    
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
	
    @Bean
    public Job importJob() {
        return jobBuilderFactory.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .listener(myJobListener())
                .build();
    }

    @Bean
    public JobExecutionListener myJobListener() {
        return new MyJobListener();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .<Person, Person>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    //reader,processor,writer三兄弟
    @Bean
    @StepScope
    public FlatFileItemReader<Person> reader() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("files/person.csv"));
        reader.setLineMapper((s, i) -> {
            String[] ss = s.split(",");
            Person person = new Person();
            person.setName(ss[0]);
            person.setAge(ss[1]);
            person.setCreateTime(sdf.parse(ss[2]));
            return person;
        });
        return reader;
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