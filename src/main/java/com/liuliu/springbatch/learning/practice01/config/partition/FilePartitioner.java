package com.liuliu.springbatch.learning.practice01.config.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class FilePartitioner implements Partitioner {

	///////////////////////////// 方法区 ////////////////////////////////////

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext value = new ExecutionContext();
			String fileName = "files/person" + i + ".csv";
			value.put("input.file.path", fileName);
			result.put("partition" + i, value);
		}
		return result;
	}
}
