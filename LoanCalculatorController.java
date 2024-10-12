package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoanCalculatorController {

    @GetMapping("/loan-calculator")
    public String loanCalculator() {
        return "loanCalculator"; // Return the loan calculator page
    }

    @PostMapping("/loan-calculate")
    public String calculateLoan(
            @RequestParam("principal") double principal,
            @RequestParam("rate") double rate,
            @RequestParam("years") int years,
            Model model
    ) {
        double monthlyRate = rate / 100 / 12;
        double numberOfPayments = years * 12;
        double monthlyPayment = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -numberOfPayments));

        model.addAttribute("monthlyPayment", monthlyPayment);

        return "loanResult"; // Return the loan result page
    }
}
