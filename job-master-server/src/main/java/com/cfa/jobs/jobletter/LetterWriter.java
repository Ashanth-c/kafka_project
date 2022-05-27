package com.cfa.jobs.jobletter;

import com.cfa.objects.letter.Letter;
import com.cfa.objects.letter.LetterController;
import com.cfa.objects.letter.LetterRepository;
import com.cfa.objects.letter.LetterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.InjectService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class LetterWriter implements ItemWriter <Letter> {
    @Autowired
    LetterController letterController;

    @Override
    public void write(List<? extends Letter> list) throws Exception {
        for (Letter letter : list){
            letterController.postLetter(letter);
            writeOnFile("The message '"+letter.getMessage()+"' has been treated\n");
            log.info("Message sent : " + letter.getMessage());
        }
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void writeOnFile(String msg) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("resultFile.txt",true));
        writer.write(msg);
        writer.close();
    }

}
