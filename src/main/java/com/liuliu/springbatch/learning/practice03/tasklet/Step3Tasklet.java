package com.liuliu.springbatch.learning.practice03.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class Step3Tasklet implements Tasklet {

	private static Logger logger = LoggerFactory.getLogger(Step3Tasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Start : " + getClass().getName() + " : execute()"); 
		return RepeatStatus.FINISHED;
	}

}
