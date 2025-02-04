package com.example.bookservice.kafka;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.service.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = {"com.example.service.BookService"})
public class BookKafka {
    private static final Logger logger = LoggerFactory.getLogger(BookKafka.class);
    private final BookService BookService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.book:book-events}")
    private String topic;

    public BookKafka(KafkaTemplate<String, String> kafkaTemplate, BookService bookservice) {
        this.kafkaTemplate = kafkaTemplate;
        this.BookService = bookservice;
    }
    public void sendDeleteBookEvent(Long bookId) {
        String event = String.format("{\"event\":\"BOOK_DELETED\",\"bookId\":\"%s\"}", bookId);
        logger.info("Producing book deleted event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    @KafkaListener(topics = "borrowing-events", groupId = "book-group")
    public void consumeBorrowingDeletedEvent(String message) {
        logger.info("Received borrowing deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BORROWING_DELETED".equals(event.get("event").asText())) {
                String bookIdStr = event.get("borrowingId").asText();
                Long bookId = Long.valueOf(bookIdStr);

                BookDto bookDto = BookService.getBookDto(bookId);

                if (bookDto != null) {
                    logger.info("Processing change to available : {}", bookId);
                    BookService.updateAvaiable(bookId, true);
                }
            }
        } catch (Exception e) {
            logger.error("Error processing book event to available", e);
        }
    }

    @KafkaListener(topics = "borrowing-events", groupId = "book-group")
    public void consumeBorrowingCreateEvent(String message) {
        logger.info("Received borrowing create event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BORROWING_CREATED".equals(event.get("event").asText())) {
                String bookIdStr = event.get("borrowingId").asText();
                Long bookId = Long.valueOf(bookIdStr);

                BookDto bookDto = BookService.getBookDto(bookId);

                if (bookDto != null) {
                    logger.info("Processing change to unavailable : {}", bookId);
                    BookService.updateAvaiable(bookId, false);
                }
            }
        } catch (Exception e) {
            logger.error("Error processing book event to unavailable", e);
        }
    }
}
