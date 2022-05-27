package com.cfa.letterjobservice;

import com.cfa.objects.letter.Letter;
import com.cfa.objects.letter.LetterController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class LetterWriter implements ItemWriter<Letter> {

    @Autowired
    LetterController letterController;

    @Override
    public void write(List<? extends Letter> list) throws Exception {
        for (Letter letter : list){
            letterController.postLetter(letter);
            writeOnFile("The message '"+letter.getMessage()+"' has been treated\n");
            log.info("Message sent : " + letter.getMessage());
        }
    }

    public void writeOnFile(String msg) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("resultFile.txt",true));
        writer.write(msg);
        writer.close();
    }

}
