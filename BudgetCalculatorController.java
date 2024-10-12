package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BudgetCalculatorController {

    @GetMapping("/budget-calculator")
    public String budgetCalculator() {
        return "budgetCalculator"; // Return the budget calculator page
    }

    @PostMapping("/budget-calculate")
    public String calculateBudget(
            @RequestParam("income") double income,
            @RequestParam("expenses") double expenses,
            Model model
    ) {
        double savings = income - expenses;
        model.addAttribute("savings", savings);
        model.addAttribute("status", savings >= 0 ? "You are within budget!" : "You are over budget!");

        return "budgetResult"; // Return the budget result page
    }
}
