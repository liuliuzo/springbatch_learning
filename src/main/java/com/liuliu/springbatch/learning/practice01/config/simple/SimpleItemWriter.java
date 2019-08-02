package com.liuliu.springbatch.learning.practice01.config.simple;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;

import com.liuliu.springbatch.learning.practice01.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleItemWriter implements ItemWriter<Person> {

	List<Person> output = TransactionAwareProxyFactory.createTransactionalList();

	@Override
	public void write(List<? extends Person> list) throws Exception {
		for (Person each : list) {
			output.add(each);
			log.info("处理后结果: {}", each);
		}
	}

	public List<Person> getOutput() {
		return output;
	}
}
