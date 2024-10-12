package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmiCalculatorController {

    @GetMapping("/emi-calculator")
    public String emiCalculator() {
        return "emiCalculator"; // Return the EMI calculator page
    }

    @PostMapping("/emi-calculate")
    public String calculateEmi(
            @RequestParam("principal") double principal,
            @RequestParam("rate") double rate,
            @RequestParam("years") int years,
            Model model
    ) {
        double monthlyRate = rate / 100 / 12;
        double numberOfPayments = years * 12;
        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, numberOfPayments)) /
                (Math.pow(1 + monthlyRate, numberOfPayments) - 1);

        model.addAttribute("emi", emi);

        return "emiResult"; // Return the EMI result page
    }
}
