package com.cfa.jobs.jobsimple;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TaskletSimple implements Tasklet {
        @Override
        public RepeatStatus execute(final StepContribution stepContribution, final ChunkContext chunkContext) {
            return RepeatStatus.FINISHED;
        }
    }