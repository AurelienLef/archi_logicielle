package com.example.borrowingservice.reporsitory;

import com.example.borrowingservice.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByUserId(Long userId);
    void deleteByUserId(Long userId);

    Borrowing findByBookId(Long bookId);
    void deleteByBookId(Long bookId);
}
