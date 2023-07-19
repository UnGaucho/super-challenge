package com.interview.demo.service;

import java.util.Random;

public class MockPercentageService implements PercentageService {

    private static final double rangeMax = 100;

    private static final double rangeMin = 0;

    @Override
    public double getPercentage() {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
}
