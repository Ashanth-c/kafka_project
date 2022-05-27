package com.cfa.letterjobservice;

import com.cfa.objects.letter.Letter;
import com.cfa.objects.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import java.sql.Date;

@Slf4j
public class LetterProcessor implements ItemProcessor<Message, Letter> {

    public Letter process(Message msg){
        Date date = new Date(System.currentTimeMillis());
        Letter letter = new Letter(date,date,msg.getMessage());
        return letter;
    }
}