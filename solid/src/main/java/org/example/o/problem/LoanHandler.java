package org.example.o.problem;

public class LoanHandler {
    private PersonalLoanValidator personalLoanValidator;
    private GageLoanValidator gageLoanValidator;

    public void approvePersonalLoan(User user) {
        if (personalLoanValidator.isValidUserPersonal(user)) {
            System.out.println("Loan validator");
        }
    }

    public void approveGageLoan(User user) {
        if (gageLoanValidator.isValiUserGage(user)) {
            System.out.println("Loan handler");
        }
    }
}
