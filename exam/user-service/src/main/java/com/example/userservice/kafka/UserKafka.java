package com.example.userservice.kafka;

import com.example.userservice.dto.UserDetailDto;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafka {
    private static final Logger logger = LoggerFactory.getLogger(UserKafka.class);
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.account:account-events}")
    private String topic;

    public UserKafka(KafkaTemplate<String, String> kafkaTemplate, UserService userService) {
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService; // Injecter le service UserService
    }
    public void sendDeleteUserEvent(Long userId) {
        String event = String.format("{\"event\":\"ACCOUNT_DELETED\",\"accountId\":\"%s\"}", userId);
        logger.info("Producing user deleted event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    @KafkaListener(topics = "borrowing-events", groupId = "account-group")
    public void consumeBorrowingDeletedEvent(String message) {
        logger.info("Received borrowing deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BORROWING_DELETED".equals(event.get("event").asText())) {
                String userIdStr = event.get("borrowingId").asText();
                Long userId = Long.valueOf(userIdStr);

                UserDetailDto userDetailDto = userService.getUserDetailById(userId);

                // ✅ Récupérer les emprunts associés
                if (userDetailDto != null) {
                    if (userDetailDto.getBorrowings().size() == 6){
                        logger.info("Processing change nombreMaxEmprunt : {}", userId);
                        userService.updateUserLock(userId, false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing account deleted event", e);
        }
    }
}
