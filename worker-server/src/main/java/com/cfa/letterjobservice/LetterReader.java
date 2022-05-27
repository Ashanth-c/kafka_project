package com.cfa.letterjobservice;

import com.cfa.objects.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

@Slf4j
public class LetterReader extends FlatFileItemReader {

    public FlatFileItemReader<Message> reader() {
        log.info("reading");
        FlatFileItemReader<Message> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("letterData.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<>() { {
            setLineTokenizer(new DelimitedLineTokenizer() { {
                setNames(new String[] {"message"});
            } });
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() { {
                setTargetType(Message.class);
            } });
        } });
        return reader;
    }
}