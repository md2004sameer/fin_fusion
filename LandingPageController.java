package com.project.taxCalc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

    @GetMapping("/")
    public String home() {
        return "index"; // Return the landing page
    }
}
