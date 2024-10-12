package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Tax2CalculatorController {

    @GetMapping("/tax-calculate2")
    public String taxCalculator() {
        return "taxCalculate2"; // Return the tax calculator page
    }

    @PostMapping("/tax-calculate2")
    public String calculateTax(
            @RequestParam("grossSalary") double grossSalary,
            @RequestParam("otherIncome") double otherIncome,
            @RequestParam("interestIncome") double interestIncome,
            @RequestParam("rentalIncome") double rentalIncome,
            @RequestParam("shortTermCapitalGains") double shortTermCapitalGains,
            @RequestParam("longTermCapitalGains") double longTermCapitalGains,
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
        double totalIncome = grossSalary + otherIncome + interestIncome + rentalIncome +
                shortTermCapitalGains + longTermCapitalGains;

        boolean isMetroCity = metroCity.equalsIgnoreCase("yes");
        double hraExemption = calculateHRAExemption(basicSalary, daReceived, hraReceived, rentPaid, isMetroCity);

        // Calculate taxable income under the old regime
        double taxableIncomeOld = totalIncome - deductions80C - npsDeduction - medicalInsurance
                - charityDonation - eduLoanInterest - savingsInterest - hraExemption;

        taxableIncomeOld = Math.max(taxableIncomeOld, 0); // Ensure non-negative taxable income
        double taxOldRegime = calculateOldTaxRegime(taxableIncomeOld, shortTermCapitalGains, longTermCapitalGains);

        // Calculate taxable income under the new regime (without deductions)
        double taxableIncomeNew = totalIncome; // New regime does not allow most deductions
        double taxNewRegime = calculateNewTaxRegime(taxableIncomeNew);

        model.addAttribute("taxOldRegime", taxOldRegime);
        model.addAttribute("taxNewRegime", taxNewRegime);
        model.addAttribute("bestRegime", taxOldRegime < taxNewRegime ? "Old Regime" : "New Regime");

        return "taxResult"; // Return the tax result page
    }

    // Old tax regime calculation including capital gains
    private double calculateOldTaxRegime(double taxableIncome, double shortTermCapitalGains, double longTermCapitalGains) {
        double tax = 0;

        // Section 87A rebate for incomes up to ₹5 lakh
        if (taxableIncome <= 500000) {
            return 0; // No tax for this income level
        }

        // Taxation for short-term capital gains (STCG)
        tax += shortTermCapitalGains * 0.15; // 15% tax rate on STCG

        // Taxation for long-term capital gains (LTCG) over ₹1 lakh
        double taxableLTCG = Math.max(0, longTermCapitalGains - 100000);
        tax += taxableLTCG * 0.10; // 10% tax on LTCG above ₹1 lakh

        // Apply standard slab rates for remaining taxable income
        if (taxableIncome > 1000000) {
            tax += (taxableIncome - 1000000) * 0.30;
            taxableIncome = 1000000;
        }
        if (taxableIncome > 500000) {
            tax += (taxableIncome - 500000) * 0.20;
            taxableIncome = 500000;
        }
        if (taxableIncome > 250000) {
            tax += (taxableIncome - 250000) * 0.05;
        }

        // Adding 4% Health and Education Cess
        return tax + (tax * 0.04);
    }

    // New tax regime calculation with updated slabs
    private double calculateNewTaxRegime(double taxableIncome) {
        double tax = 0;

        // Section 87A rebate for incomes up to ₹5 lakh
        if (taxableIncome <= 500000) {
            return 0; // No tax for this income level
        }

        // Apply tax slabs for new regime
        if (taxableIncome > 1500000) {
            tax += (taxableIncome - 1500000) * 0.30;
            taxableIncome = 1500000;
        }
        if (taxableIncome > 1250000) {
            tax += (taxableIncome - 1250000) * 0.25;
            taxableIncome = 1250000;
        }
        if (taxableIncome > 1000000) {
            tax += (taxableIncome - 1000000) * 0.20;
            taxableIncome = 1000000;
        }
        if (taxableIncome > 750000) {
            tax += (taxableIncome - 750000) * 0.15;
            taxableIncome = 750000;
        }
        if (taxableIncome > 500000) {
            tax += (taxableIncome - 500000) * 0.10;
            taxableIncome = 500000;
        }
        if (taxableIncome > 250000) {
            tax += (taxableIncome - 250000) * 0.05;
        }

        // Adding 4% Health and Education Cess
        return tax + (tax * 0.04);
    }

    // HRA Exemption Calculation based on rules
    private double calculateHRAExemption(double basicSalary, double daReceived, double hraReceived, double rentPaid, boolean isMetroCity) {
        double hraExemption = Math.min(hraReceived, Math.min(
                (isMetroCity ? 0.50 : 0.40) * (basicSalary + daReceived), // HRA exemption limit
                rentPaid - 0.10 * (basicSalary + daReceived) // Actual rent minus 10% of basic + DA
        ));
        return Math.max(hraExemption, 0); // Ensure non-negative HRA exemption
    }
}
