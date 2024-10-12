package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InvestmentCalculatorController {

    @GetMapping("/investment-calculator")
    public String investmentCalculator() {
        return "investmentCalculator"; // Return the investment calculator page
    }

    @PostMapping("/investment-calculate")
    public String calculateInvestment(
            @RequestParam("initialAmount") double initialAmount,
            @RequestParam("monthlyInvestment") double monthlyInvestment,
            @RequestParam("rate") double rate,
            @RequestParam("years") int years,
            Model model
    ) {
        double futureValue = initialAmount * Math.pow(1 + rate / 100, years);
        int months = years * 12;
        for (int i = 0; i < months; i++) {
            futureValue += monthlyInvestment * Math.pow(1 + rate / 100, (years - (i + 1) / 12.0));
        }

        model.addAttribute("futureValue", futureValue);

        return "investmentResult"; // Return the investment result page
    }
}
