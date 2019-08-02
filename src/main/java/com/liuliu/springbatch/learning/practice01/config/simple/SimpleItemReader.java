package com.liuliu.springbatch.learning.practice01.config.simple;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.liuliu.springbatch.learning.practice01.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleItemReader implements ItemReader<Person>, ItemStream {

	List<Person> items;
	
	int currentIndex = 0;
	
	private static final String CURRENT_INDEX = "current.index";

	public SimpleItemReader(List<Person> items) {
		this.items = items;
	}
	
	@Override
	public Person read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {		
	    if (currentIndex < items.size()) {
            return items.get(currentIndex++);
        }
		log.info("==> read:{},currentIndex:{}", items,currentIndex);
		return null;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (executionContext.containsKey(CURRENT_INDEX)) {
			currentIndex = new Long(executionContext.getLong(CURRENT_INDEX)).intValue();
		} else {
			currentIndex = 0;
		}
		log.info("==> open:{},currentIndex:{}", items,currentIndex);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.putLong(CURRENT_INDEX, new Long(currentIndex).longValue());
		log.info("==> update:{},currentIndex:{}", items,currentIndex);
	}

	@Override
	public void close() throws ItemStreamException {
		log.info("==> close:{},currentIndex:{}", items,currentIndex);
	}
}
