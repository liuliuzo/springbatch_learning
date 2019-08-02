package com.liuliu.springbatch.learning.practice00.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

public class MyChunkListener {

	@BeforeChunk
	public void beforeChunk(ChunkContext context) {
		System.out.println(context.getStepContext().getStepName() + "chunk before running.....");
	}

	@AfterChunk
	public void afterChunk(ChunkContext context) {
		System.out.println(context.getStepContext().getStepName() + "chunk after running.....");
	}

}
