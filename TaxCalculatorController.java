package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaxCalculatorController {

    @GetMapping("/tax-calculator")
    public String taxCalculator() {
        return "taxCalculator"; // Return the tax calculator page
    }

    @PostMapping("/tax-calculate")
    public String calculateTax(
            @RequestParam("grossSalary") double grossSalary,
            @RequestParam("otherIncome") double otherIncome,
            @RequestParam("interestIncome") double interestIncome,
            @RequestParam("rentalIncome") double rentalIncome,
            @RequestParam("deductions80C") double deductions80C,
            @RequestParam("npsDeduction") double npsDeduction,
            @RequestParam("medicalInsurance") double medicalInsurance,
            @RequestParam("charityDonation") double charityDonation,
            @RequestParam("eduLoanInterest") double eduLoanInterest,
            @RequestParam("savingsInterest") double savingsInterest,
            @RequestParam("basicSalary") double basicSalary,
            @RequestParam("daReceived") double daReceived,
            @RequestParam("hraReceived") double hraReceived,
            @RequestParam("rentPaid") double rentPaid,
            @RequestParam("metroCity") String metroCity,
            Model model
    ) {
        double totalIncome = grossSalary + otherIncome + interestIncome + rentalIncome;
        boolean isMetroCity = metroCity.equalsIgnoreCase("yes");
        double hraExemption = calculateHRAExemption(basicSalary, daReceived, hraReceived, rentPaid, isMetroCity);

        // Calculate taxable income under the old regime
        double taxableIncomeOld = totalIncome - deductions80C - npsDeduction - medicalInsurance
                - charityDonation - eduLoanInterest - savingsInterest - hraExemption;
        taxableIncomeOld = Math.max(taxableIncomeOld, 0);
        double taxOldRegime = calculateOldTaxRegime(taxableIncomeOld);

        // Calculate taxable income under the new regime (without deductions)
        double taxableIncomeNew = totalIncome; // New regime does not allow most deductions
        double taxNewRegime = calculateNewTaxRegime(taxableIncomeNew);

        model.addAttribute("taxOldRegime", taxOldRegime);
        model.addAttribute("taxNewRegime", taxNewRegime);
        model.addAttribute("bestRegime", taxOldRegime < taxNewRegime ? "Old Regime" : "New Regime");

        return "taxResult"; // Return the tax result page
    }

    // Old tax regime calculation with updated slabs and surcharge
    private double calculateOldTaxRegime(double taxableIncome) {
        double tax = 0;

        // Section 87A rebate
        if (taxableIncome <= 500000) {
            return 0;
        }

        if (taxableIncome > 1000000) {
            tax += (taxableIncome - 1000000) * 0.30; // 30% for income above 10L
            taxableIncome = 1000000;
        }
        if (taxableIncome > 500000) {
            tax += (taxableIncome - 500000) * 0.20; // 20% for income above 5L
            taxableIncome = 500000;
        }
        if (taxableIncome > 250000) {
            tax += (taxableIncome - 250000) * 0.05; // 5% for income above 2.5L
        }

        return tax + (tax * 0.04); // Adding 4% Health and Education Cess
    }

    // New tax regime calculation with updated slabs
    private double calculateNewTaxRegime(double taxableIncome) {
        double tax = 0;

        // Section 87A rebate
        if (taxableIncome <= 500000) {
            return 0;
        }

        if (taxableIncome > 1500000) {
            tax += (taxableIncome - 1500000) * 0.30; // 30% for income above 15L
            taxableIncome = 1500000;
        }
        if (taxableIncome > 1250000) {
            tax += (taxableIncome - 1250000) * 0.25; // 25% for income above 12.5L
            taxableIncome = 1250000;
        }
        if (taxableIncome > 1000000) {
            tax += (taxableIncome - 1000000) * 0.20; // 20% for income above 10L
            taxableIncome = 1000000;
        }
        if (taxableIncome > 750000) {
            tax += (taxableIncome - 750000) * 0.15; // 15% for income above 7.5L
            taxableIncome = 750000;
        }
        if (taxableIncome > 500000) {
            tax += (taxableIncome - 500000) * 0.10; // 10% for income above 5L
            taxableIncome = 500000;
        }
        if (taxableIncome > 250000) {
            tax += (taxableIncome - 250000) * 0.05; // 5% for income above 2.5L
        }

        return tax + (tax * 0.04); // Adding 4% Health and Education Cess
    }

    // HRA Exemption Calculation based on rules
    private double calculateHRAExemption(double basicSalary, double daReceived, double hraReceived, double rentPaid, boolean isMetroCity) {
        double hraExemption = Math.min(hraReceived, Math.min(
                (isMetroCity ? 0.50 : 0.40) * (basicSalary + daReceived), // 50% or 40% of Basic + DA for metro/non-metro
                rentPaid - 0.10 * (basicSalary + daReceived) // Rent paid minus 10% of Basic + DA
        ));
        return Math.max(hraExemption, 0);
    }
}
