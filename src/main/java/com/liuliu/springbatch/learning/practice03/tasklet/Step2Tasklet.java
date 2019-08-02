package com.liuliu.springbatch.learning.practice03.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.liuliu.springbatch.learning.practice03.model.UserBO;

public class Step2Tasklet implements Tasklet {

	@Autowired
	ItemStreamReader<UserBO>	itemReader;
	
	private static Logger logger = LoggerFactory.getLogger(Step2Tasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Start : " + getClass().getName() + " : execute()"); 
		return RepeatStatus.FINISHED;
	}

}
