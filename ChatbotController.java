package com.project.taxCalc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ChatbotController {

    // Example endpoint for fetching the list of topics
    @GetMapping("/api/topics")
    public List<Map<String, String>> getTopics() {
        List<Map<String, String>> topics = new ArrayList<>();

        // Sample topics (You could retrieve these from a database or a service)
        Map<String, String> topic1 = new HashMap<>();
        topic1.put("name", "Income Tax");
        topics.add(topic1);

        Map<String, String> topic2 = new HashMap<>();
        topic2.put("name", "SIP Investment");
        topics.add(topic2);

        Map<String, String> topic3 = new HashMap<>();
        topic3.put("name", "Loan Calculations");
        topics.add(topic3);

        Map<String, String> topic4 = new HashMap<>();
        topic4.put("name", "Retirement Planning");
        topics.add(topic4);

        // Add more topics as needed
        return topics;
    }

    // Example endpoint for fetching subtopics based on a selected topic
    @GetMapping("/api/subtopics")
    public List<String> getSubtopics(@RequestParam String topic) {
        List<String> subtopics = new ArrayList<>();

        // Provide different subtopics based on the selected topic
        if (topic.equalsIgnoreCase("Income Tax")) {
            subtopics.add("Tax Filing");
            subtopics.add("Tax Savings");
            subtopics.add("Income Tax Slabs");
        } else if (topic.equalsIgnoreCase("SIP Investment")) {
            subtopics.add("SIP Basics");
            subtopics.add("SIP Calculator");
            subtopics.add("SIP vs Lump Sum Investment");
        } else if (topic.equalsIgnoreCase("Loan Calculations")) {
            subtopics.add("EMI Calculator");
            subtopics.add("Loan Eligibility");
            subtopics.add("Interest Rates");
        } else if (topic.equalsIgnoreCase("Retirement Planning")) {
            subtopics.add("Retirement Fund Calculation");
            subtopics.add("NPS vs PPF");
            subtopics.add("Retirement Expenses");
        } else {
            // Default response if no match found
            subtopics.add("Sorry, no subtopics available for this topic.");
        }

        return subtopics;
    }

    // Example endpoint for fetching the solution for a given subtopic
    @GetMapping("/api/solution")
    public String getSolution(@RequestParam String subtopic) {
        String solution = "Sorry, no solution available for this subtopic.";

        // Return specific solutions based on the subtopic
        if (subtopic.equalsIgnoreCase("Tax Filing")) {
            solution = "Tax filing can be done online through the Income Tax department website or via a tax consultant.";
        } else if (subtopic.equalsIgnoreCase("SIP Basics")) {
            solution = "SIP (Systematic Investment Plan) is a way to invest in mutual funds at regular intervals.";
        } else if (subtopic.equalsIgnoreCase("EMI Calculator")) {
            solution = "An EMI Calculator helps you calculate the monthly installments for your loan based on interest rates and tenure.";
        } else if (subtopic.equalsIgnoreCase("Retirement Fund Calculation")) {
            solution = "You can calculate your retirement corpus by considering factors like monthly expenses, inflation, and number of years to retirement.";
        }

        return solution;
    }
}
