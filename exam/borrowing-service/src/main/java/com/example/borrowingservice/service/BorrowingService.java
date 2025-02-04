package com.example.borrowingservice.service;

import com.example.borrowingservice.dto.BorrowingDto;
import com.example.borrowingservice.entity.Borrowing;

import java.util.List;

public interface BorrowingService {
    public List<Borrowing> getAllBorrowings();
    public Borrowing getBorrowingById(Long id);
    public List<Borrowing> getBorrowingByUserId(Long userId);
    public Borrowing getBorrowingByBookId(Long bookId);
    public BorrowingDto getBorrowingDto(Long id);
    public List<BorrowingDto> getAllBorrowingDto();
    public List<BorrowingDto> getBorrowingDtoByUserId(Long userId);
    public BorrowingDto getBorrowingDtoByBookId(Long bookId);
    public Borrowing createBorrowing(Borrowing borrowing);
    public void deleteBorrowing(Long id);
    public void deleteBorrowingByUserId(Long userId);
    public void deleteBorrowingByBookId(Long bookId);
}
