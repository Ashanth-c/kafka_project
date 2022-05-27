package com.cfa.jobs.jobsimple;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SimpleReader implements ItemReader <String> {

    @Override
    public String read(){
        return "hello";
    }

}
