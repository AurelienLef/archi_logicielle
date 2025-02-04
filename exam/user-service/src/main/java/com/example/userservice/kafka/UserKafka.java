package com.example.userservice.kafka;

import com.example.userservice.dto.UserDetailDto;
import com.example.userservice.enums.MembershipType;
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

    @Value("${spring.kafka.topic.user:user-events}")
    private String topic;

    public UserKafka(KafkaTemplate<String, String> kafkaTemplate, UserService userService) {
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService;
    }

    public void sendDeleteUserEvent(Long userId) {
        String event = String.format("{\"event\":\"USER_DELETED\",\"userId\":\"%s\"}", userId);
        logger.info("Producing user deleted event: {}", event);
        kafkaTemplate.send(topic, event);
    }

    @KafkaListener(topics = "borrowing-events", groupId = "user-group")
    public void consumeBorrowingDeletedEvent(String message) {
        logger.info("Received borrowing deleted event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BORROWING_DELETED".equals(event.get("event").asText())) {
                String userIdStr = event.get("borrowingId").asText();
                Long userId = Long.valueOf(userIdStr);

                UserDetailDto userDetailDto = userService.getUserDetailById(userId);

                if (userDetailDto != null) {
                    if (userDetailDto.getMembershipType() == MembershipType.REGULAR) {
                        if (userDetailDto.getBorrowings().size() == 4) {
                            logger.info("Processing change nombreMaxEmprunt to false : {}", userId);
                            userService.updateUserLock(userId, false);
                        }
                    }else {
                        if (userDetailDto.getBorrowings().size() == 6) {
                            logger.info("Processing change nombreMaxEmprunt to false : {}", userId);
                            userService.updateUserLock(userId, false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing user event to false", e);
        }
    }

    @KafkaListener(topics = "borrowing-events", groupId = "user-group")
    public void consumeBorrowingCreateEvent(String message) {
        logger.info("Received borrowing create event: {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode event = objectMapper.readTree(message);

            if ("BORROWING_CREATED".equals(event.get("event").asText())) {
                String userIdStr = event.get("borrowingId").asText();
                Long userId = Long.valueOf(userIdStr);

                UserDetailDto userDetailDto = userService.getUserDetailById(userId);

                if (userDetailDto != null) {
                    if (userDetailDto.getMembershipType() == MembershipType.REGULAR) {
                        if (userDetailDto.getBorrowings().size() == 5) {
                            logger.info("Processing change nombreMaxEmprunt to true : {}", userId);
                            userService.updateUserLock(userId, true);
                        }
                    }else {
                        if (userDetailDto.getBorrowings().size() == 7) {
                            logger.info("Processing change nombreMaxEmprunt to true : {}", userId);
                            userService.updateUserLock(userId, true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing user event nombreMaxEmprunt to true", e);
        }
    }
}
