package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SipCalculatorController {

    @GetMapping("/sip-calculator")
    public String sipCalculator() {
        return "sipCalculator"; // Return the SIP calculator page
    }

    @PostMapping("/sip-calculate")
    public String calculateSip(
            @RequestParam("monthlyInvestment") double monthlyInvestment,
            @RequestParam("investmentDuration") int investmentDuration,
            @RequestParam("expectedReturn") double expectedReturn,
            Model model
    ) {
        double totalInvestment = monthlyInvestment * investmentDuration * 12;
        double futureValue = calculateFutureValue(monthlyInvestment, expectedReturn / 100, investmentDuration);

        model.addAttribute("totalInvestment", totalInvestment);
        model.addAttribute("futureValue", futureValue);
        model.addAttribute("returns", futureValue - totalInvestment);

        return "sipResult"; // Return the SIP result page
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
