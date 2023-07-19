package com.interview.demo.controller;

import com.interview.demo.dto.SumResponseDTO;
import com.interview.demo.service.CalcService;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SumController {

    private final CalcService calcService;

    public SumController(CalcService sum) {
        this.calcService = sum;
    }

    @PostMapping(path = "/sum", produces = MediaType.APPLICATION_JSON_VALUE)
    public SumResponseDTO sum(
            @RequestParam(value = "a") double a,
            @RequestParam(value = "b") double b) {

        return new SumResponseDTO(calcService.sum(a,b));
    }
}