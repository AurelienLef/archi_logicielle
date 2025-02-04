package com.example.userservice.rest;

import com.example.userservice.dto.BorrowingDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@Service
public class userServiceRest {
    private final RestTemplate restTemplate;
    private static final String BORROW_URL = "http://borrowing-service/byUser";

    public userServiceRest(RestTemplate restTemplate, RestTemplate restTemplate1) {
        this.restTemplate = restTemplate1;
    }

    public List<BorrowingDto> getBorrowingByUserId(Long userId) {
        try {
            ResponseEntity<List<BorrowingDto>> response = restTemplate.exchange(
                    BORROW_URL + userId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<BorrowingDto>>() {});
            return response.getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
