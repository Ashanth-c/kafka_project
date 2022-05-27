package com.cfa.remotepartition;

import com.cfa.letterjobservice.LetterProcessor;
import com.cfa.letterjobservice.LetterReader;
import com.cfa.letterjobservice.LetterWriter;
import com.cfa.objects.letter.Letter;
import com.cfa.objects.letter.LetterController;
import com.cfa.objects.message.Message;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

@Configuration
@EnableBatchIntegration
@EnableBatchProcessing
public class LetterPartitionConfig {
    public static String TOPIC = "step-execution-events-lol";
    public static String GROUP_ID = "step-response_partition";

    @Autowired
    private RemotePartitioningWorkerStepBuilderFactory workerBuilder;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private ConsumerFactory kafkaFactory;
    @Autowired
    LetterController letterController;

    @Bean
    public Step workerStep() {
        return this.workerBuilder.get("workerStep")
                .inputChannel(requests())
                .outputChannel(replies())
                .<Message, Letter>chunk(1)
                .reader(new LetterReader().reader())
                .processor(new LetterProcessor())
                .writer(new LetterWriter(letterController))
                .build();
    }

    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow() {
        final ContainerProperties containerProps = new ContainerProperties(TOPIC);
        containerProps.setGroupId(GROUP_ID);

        final KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(kafkaFactory, containerProps);
        final KafkaMessageDrivenChannelAdapter kafkaMessageChannel = new KafkaMessageDrivenChannelAdapter(container);

        return IntegrationFlows
                .from(kafkaMessageChannel)
                .channel(requests())
                .get();
    }

    @Bean
    public IntegrationFlow outboundFlow() {
        final KafkaProducerMessageHandler kafkaMessageHandler = new KafkaProducerMessageHandler(kafkaTemplate);
        kafkaMessageHandler.setTopicExpression(new LiteralExpression(PartitionConfig.TOPIC));
        return IntegrationFlows
                .from(replies())
                .handle(kafkaMessageHandler)
                .get();
    }

}

