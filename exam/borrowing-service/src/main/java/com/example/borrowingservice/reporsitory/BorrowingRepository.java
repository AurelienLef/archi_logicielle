package com.example.borrowingservice.reporsitory;

import com.example.borrowingservice.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
}
