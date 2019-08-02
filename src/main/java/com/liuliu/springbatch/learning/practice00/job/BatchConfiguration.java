package com.liuliu.springbatch.learning.practice00.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.liuliu.springbatch.learning.practice00.entity.People;
import com.liuliu.springbatch.learning.practice00.listener.JobCompletionNotificationListener;
import com.liuliu.springbatch.learning.practice00.step.PeopleItemProcessor;

//@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<People> writer) {
        return stepBuilderFactory.get("step1")
            .<People, People> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }
    
    @Bean
    public Step step2(JdbcBatchItemWriter<People> writer) {
        return stepBuilderFactory.get("step2")
            .<People, People> chunk(2)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }
    // end::jobstep[]
    
    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<People> reader() {
        return new FlatFileItemReaderBuilder<People>()
            .name("personItemReader")
            .resource(new ClassPathResource("files/sample-data.csv"))
            .delimited()
            .names(new String[]{"firstName", "lastName"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<People>() {{
                setTargetType(People.class);
            }}).build();
    }

    @Bean
    public PeopleItemProcessor processor() {
        return new PeopleItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<People> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<People>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]
}
