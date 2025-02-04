package com.example.bookservice.controller;

import com.example.bookservice.dto.BookDto;
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

    @GetMapping
    public Book updateAvaiable(@PathVariable Long id, boolean aviable){return bookService.updateAvaiable(id, aviable);};

    @DeleteMapping("{/id}")
    public Book deleteById(@PathVariable Long id){return bookService.deleteById(id);};

    @GetMapping("{/id/details}")
    public BookDto getBookDto(@PathVariable Long id){return bookService.getBookDto(id);};
}
