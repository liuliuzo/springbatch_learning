package com.liuliu.springbatch.learning.practice01.config.simple;

import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.liuliu.springbatch.learning.practice01.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleItemProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		person.setUpCaseName(person.getName().toUpperCase());
		person.setUpdateTime(new Date());
		log.info("==> process person:{}", person);
		return person;
	}
}
