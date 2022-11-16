package com.accenture.pocvivoconsumereventbilling1.consumer;

import com.accenture.pocvivoconsumereventbilling1.model.FinancialAccountCreateEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

    @Value("${topic.name.consumer")
    private String topicName;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, String> payload){
        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
//        log.info("Order: {}", payload.value());
        try {
            FinancialAccountCreateEvent consumeToGenerateFile =  objectMapper.readValue(payload.value(), FinancialAccountCreateEvent.class);
            System.out.println(consumeToGenerateFile);
            log.info("FinancialAccountCreateEvent: {}", consumeToGenerateFile);

        } catch (IOException e) {
            log.error("Couldn't serialize response for content type application/json", e);

        }
    }

}