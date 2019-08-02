package com.liuliu.springbatch.learning.practice01.listener.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class MyJobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.debug("job {},{} 开始执行 ...", jobExecution.getJobId(), jobExecution.getJobInstance().getJobName());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.debug("job {},{} 执行成功 ...", jobExecution.getJobId(), jobExecution.getJobInstance().getJobName());

	}
}
