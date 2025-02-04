package com.example.borrowingservice.dto;

import com.example.borrowingservice.enums.MembershipType;

import java.util.List;

public class UserDetailDto {
    private String name;
    private String email;
    private MembershipType membershipType; // Regular, Premium
    private boolean isLocked;
    private List<BorrowingDto> borrowings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<BorrowingDto> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<BorrowingDto> borrowings) {
        this.borrowings = borrowings;
    }
}
