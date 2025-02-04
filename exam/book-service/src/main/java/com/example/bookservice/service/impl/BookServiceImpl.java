package com.example.bookservice.service.impl;

import com.example.bookservice.entity.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public Book findById(Long id) {
        return null;
    }

    @Override
    public Book create(Book book) {
        return null;
    }

    @Override
    public Book deleteById(Long id) {
        return null;
    }
}
