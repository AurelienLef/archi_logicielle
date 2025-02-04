package com.example.bookservice.kafka;

import com.example.bookservice.rest.BookService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BookKafka {
    private static final Logger logger = LoggerFactory.getLogger(BookKafka.class.getName());

    private BookService bookService;


}
