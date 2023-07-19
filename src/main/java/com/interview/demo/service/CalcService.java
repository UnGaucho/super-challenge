package com.interview.demo.service;

public class CalcService {

    private final PercentageService percentageService;

    public CalcService(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    public double sum(double a, double b) {
        return (a + b) * (1 + (percentageService.getPercentage() / 100));
    }
}
