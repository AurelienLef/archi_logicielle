package com.example.bookservice.service.impl;

import com.example.bookservice.dto.BookDto;
import com.example.bookservice.entity.Book;
import com.example.bookservice.kafka.BookKafka;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.rest.BookServiceRest;
import com.example.bookservice.service.BookService;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private BookServiceRest bookServiceRest;
    private BookKafka bookKafka;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateAvaiable(Long id, Boolean avaiable) {
        return bookRepository.findById(id)
                .map(user -> {
                    user.setAvailable(avaiable);
                    return bookRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book deleteById(Long id) {
        bookKafka.sendDeleteBookEvent(id);
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto getBookDto(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());
        dto.setAvailable(book.isAvailable());

        return dto;
    }
}
