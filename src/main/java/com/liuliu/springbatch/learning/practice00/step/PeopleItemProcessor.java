package com.liuliu.springbatch.learning.practice00.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.liuliu.springbatch.learning.practice00.entity.People;

public class PeopleItemProcessor implements ItemProcessor<People, People> {

	private static final Logger log = LoggerFactory.getLogger(PeopleItemProcessor.class);

	@Override
	public People process(final People People) throws Exception {
		
		final String firstName = People.getFirstName().toUpperCase();
		final String lastName = People.getLastName().toUpperCase();

		final People transformedPeople = new People(firstName, lastName);

		log.info("Converting (" + People + ") into (" + transformedPeople + ")");

		return transformedPeople;
	}

}
