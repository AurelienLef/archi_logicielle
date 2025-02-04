package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import com.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    //@Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAll(){return bookService.findAll();};

    @GetMapping("{/id}")
    public Book findById(@PathVariable Long id){return bookService.findById(id);};

    @PostMapping
    public Book create(@RequestBody Book book){return bookService.create(book);};

    @DeleteMapping("{/id}")
    public Book deleteById(@PathVariable Long id){return bookService.deleteById(id);};
}
