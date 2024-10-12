package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RetirementCalculatorController {

    @GetMapping("/retirement-calculator")
    public String retirementCalculator() {
        return "retirementCalculator"; // Return the retirement calculator page
    }

    @PostMapping("/retirement-calculate")
    public String calculateRetirement(
            @RequestParam("currentAge") int currentAge,
            @RequestParam("retirementAge") int retirementAge,
            @RequestParam("monthlySavings") double monthlySavings,
            @RequestParam("expectedReturn") double expectedReturn,
            Model model
    ) {
        int yearsToRetirement = retirementAge - currentAge;
        double futureValue = calculateFutureValue(monthlySavings, expectedReturn / 100, yearsToRetirement);

        model.addAttribute("futureValue", futureValue);

        return "retirementResult"; // Return the retirement result page
    }

    private double calculateFutureValue(double monthlyInvestment, double rateOfReturn, int years) {
        double futureValue = 0;
        int months = years * 12;
        for (int i = 1; i <= months; i++) {
            futureValue += monthlyInvestment * Math.pow(1 + rateOfReturn / 12, months - i);
        }
        return futureValue;
    }
}
