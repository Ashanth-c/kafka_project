package com.cfa.jobs.jobsimple;

import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleProcessor implements ItemProcessor<String,String> {

    public String process(String sL){
        return sL.toUpperCase();
    }

}