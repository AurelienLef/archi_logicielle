package com.example.bookservice.service;

import com.example.bookservice.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAll();
    public Book findById(Long id);
    public Book create(Book book);
    public Book deleteById(Long id);
}
