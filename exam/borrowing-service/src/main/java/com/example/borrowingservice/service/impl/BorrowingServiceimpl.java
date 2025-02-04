package com.example.borrowingservice.service.impl;

import com.example.borrowingservice.dto.BookDto;
import com.example.borrowingservice.dto.BorrowingDto;
import com.example.borrowingservice.dto.UserDetailDto;
import com.example.borrowingservice.entity.Borrowing;
import com.example.borrowingservice.kafka.BorrowingKafka;
import com.example.borrowingservice.reporsitory.BorrowingRepository;
import com.example.borrowingservice.rest.BorrowingServiceRest;
import com.example.borrowingservice.service.BorrowingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowingServiceimpl implements BorrowingService {
    private BorrowingRepository borrowingRepository;
    private BorrowingServiceRest borrowingServiceRest;
    private BorrowingKafka borrowingKafka;

    @Override
    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    @Override
    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id).get();
    }

    @Override
    public List<Borrowing> getBorrowingByUserId(Long userId) {
        return borrowingRepository.findByUserId(userId);
    }

    @Override
    public Borrowing getBorrowingByBookId(Long bookId) {
        return borrowingRepository.findByBookId(bookId);
    }

    @Override
    public BorrowingDto getBorrowingDto(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowing not found"));

        BorrowingDto dto = new BorrowingDto();
        dto.setId(borrowing.getId());
        dto.setBookId(borrowing.getBookId());
        dto.setUserId(borrowing.getUserId());
        dto.setBorrowDate(borrowing.getBorrowDate());
        dto.setReturnDate(borrowing.getReturnDate());

        return dto;
    }

    @Override
    public List<BorrowingDto> getAllBorrowingDto() {
        List<Borrowing> borrowings = borrowingRepository.findAll();
        List<BorrowingDto> borrowingDtos = new ArrayList<>();

        for (Borrowing borrowing : borrowings) {
            borrowingDtos.add(getBorrowingDto(borrowing.getId()));
        }
        return borrowingDtos;
    }

    @Override
    public List<BorrowingDto> getBorrowingDtoByUserId(Long userId) {
        List<Borrowing> borrowings = borrowingRepository.findByUserId(userId);
        List<BorrowingDto> borrowingDtos = new ArrayList<>();

        for (Borrowing borrowing : borrowings) {
            borrowingDtos.add(getBorrowingDto(borrowing.getId()));
        }
        return borrowingDtos;
    }

    @Override
    public BorrowingDto getBorrowingDtoByBookId(Long bookId) {
        return getBorrowingDto(borrowingRepository.findByBookId(bookId).getId());
    }

    @Override
    public Borrowing createBorrowing(Borrowing borrowing) {
        Long userId = borrowing.getUserId();
        Long bookId = borrowing.getBookId();
        UserDetailDto userDto = borrowingServiceRest.getUserDetail(userId);
        BookDto bookDto = borrowingServiceRest.getBook(bookId);

        if (  (userDto != null && bookDto != null) && (! userDto.isLocked() && bookDto.isAvailable()) ) {
            borrowingKafka.sendCreateBorrowingEvent(borrowing.getId());
            return borrowingRepository.save(borrowing);
        }
        return null;
    }

    @Override
    public void deleteBorrowing(Long id) {
        borrowingKafka.sendDeleteBorrowingEvent(id);
        borrowingRepository.deleteById(id);
    }

    @Override
    public void deleteBorrowingByUserId(Long userId) {
        List<Borrowing> borrowings = borrowingRepository.findByUserId(userId);
        for (Borrowing borrowing : borrowings) {
            borrowingKafka.sendDeleteBorrowingEvent(borrowing.getId());
            borrowingRepository.deleteById(borrowing.getId());
        }
    }

    @Override
    public void deleteBorrowingByBookId(Long bookId) {
        Borrowing borrowingDelete = borrowingRepository.findByBookId(bookId);
        borrowingKafka.sendDeleteBorrowingEvent(borrowingDelete.getId());
        borrowingRepository.deleteById(borrowingDelete.getId());
    }
}
