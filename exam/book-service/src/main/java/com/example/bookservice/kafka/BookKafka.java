package com.example.bookservice.kafka;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.rest.BookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookKafka {
    private static final Logger logger = LoggerFactory.getLogger(BookKafka.class);
    private final BookService bookservice;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.book:book-events}")
    private String topic;

    public BookKafka(KafkaTemplate<String, String> kafkaTemplate, BookService bookService) {
        this.kafkaTemplate = kafkaTemplate;
        this.bookservice = bookService;
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
                String userIdStr = event.get("borrowingId").asText();
                Long userId = Long.valueOf(userIdStr);

                BookDto bookDto = bookservice.

                // ✅ Récupérer les emprunts associés
                if (userDetailDto != null) {
                    if (userDetailDto.getBorrowings().size() == 6){
                        logger.info("Processing change nombreMaxEmprunt : {}", userId);
                        userService.updateUserLock(userId, false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing book event", e);
        }
    }
}
