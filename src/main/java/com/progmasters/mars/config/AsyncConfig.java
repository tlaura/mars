package com.progmasters.mars.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Value("${executor.corePoolSize}")
    private Integer corePool;

    @Value("${executor.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${executor.queueCapacity}")
    private Integer queueCapacity;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePool);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Mars-");
        executor.initialize();
        return executor;
    }
}
