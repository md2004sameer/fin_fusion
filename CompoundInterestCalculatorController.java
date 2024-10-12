package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompoundInterestCalculatorController {

    @GetMapping("/compound-interest-calculator")
    public String compoundInterestCalculator() {
        return "compoundInterestCalculator"; // Return the compound interest calculator page
    }

    @PostMapping("/compound-interest-calculate")
    public String calculateCompoundInterest(
            @RequestParam("principal") double principal,
            @RequestParam("rate") double rate,
            @RequestParam("time") int time,
            Model model
    ) {
        double amount = principal * Math.pow(1 + rate / 100, time);
        double interest = amount - principal;

        model.addAttribute("amount", amount);
        model.addAttribute("interest", interest);

        return "compoundInterestResult"; // Return the compound interest result page
    }
}
