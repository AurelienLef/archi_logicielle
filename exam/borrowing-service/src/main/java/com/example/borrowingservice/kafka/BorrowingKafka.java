package com.example.borrowingservice.kafka;

import com.example.borrowingservice.dto.BorrowingDto;
import com.example.borrowingservice.entity.Borrowing;
import com.example.borrowingservice.service.BorrowingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingKafka {
    private static final Logger logger = LoggerFactory.getLogger(BorrowingKafka.class);
    private final BorrowingService borrowingService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.borrowing:borrowing-events}")
    private String topic;

    public BorrowingKafka(KafkaTemplate kafkatemplate, BorrowingService BorrowingService) {
        this.kafkaTemplate = kafkatemplate;
        this.borrowingService = BorrowingService;
    }

    public void sendDeleteBorrowingEvent(Long borrowingId) {
        String event = String.format("{\"event\":\"BORROWING_DELETED\",\"borrowingId\":\"%s\"}", borrowingId);
        logger.info("Producing borrowing deleted event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    public void sendCreateBorrowingEvent(Long borrowingId) {
        String event = String.format("{\"event\":\"BORROWING_CREATED\",\"borrowingId\":\"%s\"}", borrowingId);
        logger.info("Producing borrowing created event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    @KafkaListener(topics = "user-events", groupId = "borrowing-group")
    public void consumeUserDeletedEvent(String message) {
        logger.info("Received user deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("USER_DELETED".equals(event.get("event").asText())) {
                String userIdStr = event.get("userId").asText();
                Long userId = Long.valueOf(userIdStr);
                logger.info("Processing deletion for borrowings of user : {}", userId);

                List<BorrowingDto> listeBorrowingsDto = borrowingService.getBorrowingDtoByUserId(userId);

                for (BorrowingDto borrowingDto : listeBorrowingsDto) {
                    borrowingService.deleteBorrowing(borrowingDto.getId());
                }
            }
        } catch (Exception e) {
            logger.error("Error processing borrowings of user deleted event", e);
        }
    }

    @KafkaListener(topics = "book-events", groupId = "borrowing-group")
    public void consumeBookDeletedEvent(String message) {
        logger.info("Received book deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BOOK_DELETED".equals(event.get("event").asText())) {
                String bookIdStr = event.get("bookId").asText();
                Long bookId = Long.valueOf(bookIdStr);
                logger.info("Processing deletion for borrowing of book : {}", bookId);

                Borrowing borrowing = borrowingService.getBorrowingByBookId(bookId);
                borrowingService.deleteBorrowing(borrowing.getId());
            }
        } catch (Exception e) {
            logger.error("Error processing borrowing of book deleted event", e);
        }
    }
}
