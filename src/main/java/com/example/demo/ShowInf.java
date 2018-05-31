package com.example.demo;


import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class ShowInf {

	private static final Logger log = LoggerFactory.getLogger(ShowInf.class);

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			log.debug("Using log4j2 ...... ?");
			System.out.println();
			System.out.println("CommandLine Runner:");
			for (String arg : args) {
				System.out.println(arg);
			}

		};
	}

	@Bean
	public ApplicationRunner appRunner() {
		return args -> {
			System.out.println();
			System.out.println("Application Runner:");
			for (String opt : args.getOptionNames()) {
				System.out.print(opt);
				System.out.print("->");
				System.out.println(args.getOptionValues(opt).stream().collect(Collectors.joining(",", "[", "]")));
			}
		};
	}

	@Autowired
	private CounterService counterService;

	@Bean
	/**
	 * xyz页面访问监听器
	 * @return
	 */
	public ApplicationListener<ApplicationEvent> helloListener() {
		final String HELLO_URL = "/xyz";

		return (ApplicationEvent event) -> {
			if (event instanceof ServletRequestHandledEvent) {
				ServletRequestHandledEvent e = (ServletRequestHandledEvent) event;
				if (e.getRequestUrl().equals(HELLO_URL))
					counterService.increment("xyz.hits");
			}
		};
	}

	@Bean
	/**
	 * 检测互联网是否可以访问
	 * @return
	 */
	public HealthIndicator myHealth() {
		return () -> {
			RestTemplate restTemplate = new RestTemplate();
			try {
				restTemplate.getForObject("https://www.baidu.com", String.class);
				return Health.up().build();
			} catch (Exception e) {
				return Health.down().withDetail("Error Code", 404).build();
			}

		};
	}

}
