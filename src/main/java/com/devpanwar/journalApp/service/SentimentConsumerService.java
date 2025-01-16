package com.devpanwar.journalApp.service;

import com.devpanwar.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

//code Kafka consumer
@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

//    this listener continuously listens to the topic weekly-sentiments and group weekly-sentiment-group
    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}
