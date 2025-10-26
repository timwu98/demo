package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalculationRequest {
    private double num1;
    private double num2;
    private String operate;
}
