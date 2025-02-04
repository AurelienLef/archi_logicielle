package com.example.bookservice.service;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAll();
    public Book findById(Long id);
    public Book create(Book book);
    public Book updateAvaiable(Long id, Boolean avaiable);
    public Book deleteById(Long id);
    public BookDto getBookDto(Long id);
}
