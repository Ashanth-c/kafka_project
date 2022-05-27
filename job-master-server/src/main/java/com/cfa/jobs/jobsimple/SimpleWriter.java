package com.cfa.jobs.jobsimple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SimpleWriter implements ItemWriter<String> {

    public void write( List<? extends  String > sL){
            for(String str : sL){
                log.info(str);
        }
    }
}