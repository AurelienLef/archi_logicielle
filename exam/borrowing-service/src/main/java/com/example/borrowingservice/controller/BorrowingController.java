package com.example.borrowingservice.controller;

import com.example.borrowingservice.entity.Borrowing;
import com.example.borrowingservice.service.BorrowingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{/borrowings}")
public class BorrowingController {
    private BorrowingService borrowingService;

    @GetMapping
    public List<Borrowing> getAllBorrowings(){return borrowingService.getAllBorrowings();};

    @GetMapping("{/id}")
    public Borrowing getBorrowingById(@PathVariable Long id){return borrowingService.getBorrowingById(id);};

    @GetMapping("{/id}")
    public Borrowing getBorrowingByUserId(@PathVariable Long userId){return borrowingService.getBorrowingByUserId(userId);};

    @PostMapping
    public Borrowing createBorrowing(@RequestBody Borrowing borrowing){return borrowingService.createBorrowing(borrowing);};

    @DeleteMapping("{/id}")
    public Borrowing deleteBorrowing(@PathVariable Long id){return borrowingService.deleteBorrowing(id);};
}
