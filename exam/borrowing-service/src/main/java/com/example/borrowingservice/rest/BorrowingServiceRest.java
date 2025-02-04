package com.example.borrowingservice.rest;

import com.example.borrowingservice.dto.BookDto;
import com.example.borrowingservice.dto.UserDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BorrowingServiceRest {
    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://user-service/users/";
    private final String bookServiceUrl = "http://book-service/books/";

    @Autowired
    public BorrowingServiceRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean usertExists(Long userId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(userServiceUrl + userId, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    public  boolean bookExists(Long bookId) {
        try{
            ResponseEntity<String> response = restTemplate.getForEntity(bookServiceUrl + bookId, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    public UserDetailDto getUserDetail(Long userId) {
        if (usertExists(userId)) {
            try{
                ResponseEntity<String> response = restTemplate.getForEntity(userServiceUrl + userId, String.class);
                return restTemplate.getForObject(bookServiceUrl + userId, UserDetailDto.class);
            } catch (HttpClientErrorException.NotFound e) {return null;}
        } else {
            return null;
        }
    }

    public BookDto getBook(Long bookId) {
        if (bookExists(bookId)) {
            try{
                ResponseEntity<String> response = restTemplate.getForEntity(bookServiceUrl + bookId, String.class);
                return restTemplate.getForObject(bookServiceUrl + bookId, BookDto.class);
            }catch (HttpClientErrorException.NotFound e) {return null;}
        } else {
            return null;
        }
    }
}
