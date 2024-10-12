package com.project.taxCalc;



import java.util.List;

public class Topic {
    private String name;
    private List<Subtopic> subtopics;

    public Topic(String name, List<Subtopic> subtopics) {
        this.name = name;
        this.subtopics = subtopics;
    }

    public String getName() {
        return name;
    }

    public List<Subtopic> getSubtopics() {
        return subtopics;
    }
}

class Subtopic {
    private String name;
    private String solution;

    public Subtopic(String name, String solution) {
        this.name = name;
        this.solution = solution;
    }

    public String getName() {
        return name;
    }

    public String getSolution() {
        return solution;
    }
}
