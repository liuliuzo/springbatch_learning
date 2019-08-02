package com.liuliu.springbatch.learning.practice01.config.partition.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.StepExecutionAggregator;

import java.util.Collection;

public class MyStepExecutionAggregator implements StepExecutionAggregator {

	private static final Logger logger = LoggerFactory.getLogger(MyStepExecutionAggregator.class);

	/**
	 * 聚合
	 *
	 * @param masterExecution masterStep 结果
	 * @param slaveExecutions slaveStep 结果集合
	 */
	@Override
	public void aggregate(StepExecution masterExecution, Collection<StepExecution> slaveExecutions) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		logger.info("masterStep = {}", masterExecution.toString());
		slaveExecutions.forEach(slaveExecution -> logger.debug(slaveExecution.toString()));
		logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
}
