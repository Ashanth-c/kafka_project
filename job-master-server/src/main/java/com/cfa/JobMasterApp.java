package com.cfa;

import com.cfa.objects.letter.LetterRepository;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class JobMasterApp {

  @Autowired
  LetterRepository letterRepository;
  public static void main(String[] args) {
    SpringApplication.run(JobMasterApp.class, args);
  }

}
