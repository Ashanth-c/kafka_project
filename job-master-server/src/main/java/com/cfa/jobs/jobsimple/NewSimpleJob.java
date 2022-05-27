package com.cfa.jobs.jobsimple;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NewSimpleJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private Source sources;

    @Bean
    public Job jobS() {
        return jobBuilderFactory
                .get("jobS")
                .start(newSimpleStep())
                .next(simpleStepTwo())
                .build();
    }

    @Bean
    public Step newSimpleStep() {
        return this.stepBuilderFactory
                .get("newSimpleStep")
                .tasklet(new TaskletSimple())
                .build();
    }

    @Bean
    public Step simpleStepTwo() {
        return this.stepBuilderFactory.get("simpleStepTwo")
                .<String,String> chunk(2)
                .reader(new SimpleReader())
                .processor(new SimpleProcessor())
                .writer(new SimpleWriter())
                .build();
    }
}
