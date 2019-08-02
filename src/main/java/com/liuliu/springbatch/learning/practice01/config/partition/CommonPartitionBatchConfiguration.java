package com.liuliu.springbatch.learning.practice01.config.partition;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.liuliu.springbatch.learning.practice01.config.partition.aggregator.MyStepExecutionAggregator;
import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemProcessor;
import com.liuliu.springbatch.learning.practice01.config.simple.SimpleItemWriter;
import com.liuliu.springbatch.learning.practice01.listener.batch.MyJobListener;
import com.liuliu.springbatch.learning.practice01.model.Person;

/**
 * 
 * Partitioner	分隔Step逻辑，即如何将大的任务转换为多个分区子任务和放置在每个子任务的上下文
 * PartitionHandler	扩展为其它执行方式，可以转化本地调用为远程调用，即jms、rpc等调用
 * PartitionReducer	可以在Step整个生命周期内做一些额外的处理，比如初始化和销毁堆内缓存
 * StepExecutionAggregator	Step最后的统计分析聚合逻辑
 *
 */
@Configuration
public class CommonPartitionBatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CommonPartitionBatchConfiguration.class);
  
    public static final int GRID_SIZE = 4;
	
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    //----------------------------------------------------------------
    //  partitionJob 配置
    //----------------------------------------------------------------
    @Bean
    public Job partitionJob(JobBuilderFactory jobs,@Qualifier("masterStep") Step masterStep) {
        return jobs.get("partitionJob")
                .start(masterStep)
                .incrementer(new RunIdIncrementer())
                .listener(new MyJobListener())
                .build();
    }
    @Bean(name = "masterStep")
    public Step masterStep(@Qualifier("slaveStep") Step slaveStep,
                           PartitionHandler partitionHandler) {
        return stepBuilderFactory.get("masterStep")
                // 配置一个PartitionStep
                .partitioner(slaveStep.getName(), new FilePartitioner())
                .partitionHandler(partitionHandler)
                .aggregator(stepExecutionAggregator())
                .build();
    }
    @Bean(name = "slaveStep")
    public Step slaveStep(FlatFileItemReader<Person> reader, ItemWriter<Person> writer,ItemProcessor<Person, Person> processor) {
        return stepBuilderFactory.get("slaveStep")
                .<Person, Person> chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    @Bean
	public PartitionHandler taskPartitionHandler(TaskExecutor taskExecutor, @Qualifier("slaveStep") Step slaveStep) {
		TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
		partitionHandler.setGridSize(GRID_SIZE);
		partitionHandler.setStep(slaveStep);
		partitionHandler.setTaskExecutor(taskExecutor);
		return partitionHandler;
	}
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setThreadNamePrefix("partitionHandler");
		return executor;
	}
    @Bean
    public MyStepExecutionAggregator stepExecutionAggregator(){
        return new MyStepExecutionAggregator();
    }

    //reader,processor,writer 三兄弟
	@Bean
	@StepScope //使用了这个才可以使用上下文中的内容
	public FlatFileItemReader<Person> reader(@Value("#{stepExecutionContext['input.file.path']}") String filePath) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource(filePath));
		reader.setLineMapper((s, i) -> {
			Person person = new Person();
			String[] tmp = s.split(",");
			person.setName(tmp[0]);
			person.setAge(tmp[1]);
			person.setCreateTime(sdf.parse(tmp[2]));
			logger.info(">>>>>>>>> 读取到数据 {}", person);
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
