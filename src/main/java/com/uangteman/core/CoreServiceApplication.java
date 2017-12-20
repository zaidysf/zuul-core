package com.uangteman.core;

import com.uangteman.core.config.PostFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class CoreServiceApplication {

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApplication.class, args);
	}
}
