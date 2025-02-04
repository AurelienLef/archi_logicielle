package com.example.borrowingservice.service;

import com.example.borrowingservice.entity.Borrowing;

import java.util.List;

public interface BorrowingService {
    public List<Borrowing> getAllBorrowings();
    public Borrowing getBorrowingById(Long id);
    public Borrowing getBorrowingByUserId(Long userId);
    public Borrowing createBorrowing(Borrowing borrowing);
    public Borrowing deleteBorrowing(Long id);
}
