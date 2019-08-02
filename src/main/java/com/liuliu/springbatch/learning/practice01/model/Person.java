package com.liuliu.springbatch.learning.practice01.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person {

	private String name;

	private String age;
	
	private String upCaseName;

	private Date createTime;

	private Date updateTime;

	public Person(String name, String age) {
		this.name = name;
		this.age = age;
		this.createTime=new Date();
	}

	public Person() {
	}
}
