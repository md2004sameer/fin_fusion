package com.project.taxCalc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaxController {

    @GetMapping("/calculateTax")
    public String calculateTax() {
        return "Tax calculation in progress!";
    }
}
