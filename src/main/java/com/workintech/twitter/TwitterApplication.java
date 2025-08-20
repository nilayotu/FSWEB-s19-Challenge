package com.workintech.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwitterApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(TwitterApplication.class, args);
		for (String bean: context.getBeanDefinitionNames()){
			System.out.println(bean);
		}
	}

}
