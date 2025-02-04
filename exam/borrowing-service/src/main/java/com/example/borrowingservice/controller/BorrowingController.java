package com.example.borrowingservice.controller;

import com.example.borrowingservice.dto.BorrowingDto;
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
    public List<Borrowing> getBorrowingByUserId(@PathVariable Long userId){return borrowingService.getBorrowingByUserId(userId);};

    @GetMapping("{/id}")
    public Borrowing getBorrowingByBookId(@PathVariable Long id){return borrowingService.getBorrowingByBookId(id);};

    @GetMapping("{/id}")
    public BorrowingDto getBorrowingDto(@PathVariable Long id){return borrowingService.getBorrowingDto(id);};

    @GetMapping
    public List<BorrowingDto> getAllBorrowingDto(){return borrowingService.getAllBorrowingDto();};

    @GetMapping("{/id}")
    public List<BorrowingDto> getBorrowingDtoByUserId(Long userId){return borrowingService.getBorrowingDtoByUserId(userId);};

    @GetMapping("{/id}")
    public BorrowingDto getBorrowingDtoByBookId(Long bookId){return borrowingService.getBorrowingDtoByBookId(bookId);};

    @PostMapping
    public Borrowing createBorrowing(@RequestBody Borrowing borrowing){return borrowingService.createBorrowing(borrowing);};

    @DeleteMapping("{/id}")
    public void deleteBorrowing(@PathVariable Long id){borrowingService.deleteBorrowing(id);};

    @DeleteMapping("{/id}")
    public void deleteBorrowingByUserId(@PathVariable Long userId){borrowingService.deleteBorrowingByUserId(userId);};

    @DeleteMapping("{/id}")
    public void deleteBorrowingByBookId(@PathVariable Long id){borrowingService.deleteBorrowingByBookId(id);};
}
