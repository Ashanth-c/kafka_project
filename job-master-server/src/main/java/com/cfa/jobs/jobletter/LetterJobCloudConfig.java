package com.cfa.jobs.jobletter;

import com.cfa.objects.letter.Letter;
import com.cfa.objects.message.Message;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
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
public class LetterJobCloudConfig {

    public static String TOPICSent = "topic-sent";
    public static String TOPICReceived = "topic-received";
    public static String GROUP_ID = "stepresponse_partition";

    @Autowired
    private RemoteChunkingManagerStepBuilderFactory managerStepBuilderFactory;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private ConsumerFactory kafkaFactory;

    @Bean
    public Job letterCloudJob() {
        return jobBuilderFactory
                .get("letterCloudJob")
                .start(letterStep())
                .build();
    }

        @Bean
        public Step letterStep() {
            return this.managerStepBuilderFactory.get("masterStep")
                    .<Message,Letter>chunk(1)
                    .reader(new LetterReader())
                    .outputChannel(requests()) // requests sent to workers
                    .inputChannel(replies()) // replies received from workers
                    .build();
        }


    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel replies() { return new QueueChannel(); }

    @Bean
    public IntegrationFlow outboundFlow() {
        final KafkaProducerMessageHandler kafkaMessageHandler = new KafkaProducerMessageHandler(kafkaTemplate);
        kafkaMessageHandler.setTopicExpression(new LiteralExpression(TOPICSent));
        return IntegrationFlows
                .from(requests())
                .handle(kafkaMessageHandler)
                .get();
    }

    @Bean
    public IntegrationFlow inboundFlow() {
        final ContainerProperties containerProps = new ContainerProperties(TOPICReceived);
        containerProps.setGroupId(GROUP_ID);

        final KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(kafkaFactory, containerProps);
        final KafkaMessageDrivenChannelAdapter kafkaMessageChannel = new KafkaMessageDrivenChannelAdapter(container);

        return IntegrationFlows
                .from(kafkaMessageChannel)
                .channel(replies())
                .get();
    }

}
