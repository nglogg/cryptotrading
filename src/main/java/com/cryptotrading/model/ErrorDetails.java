package com.cryptotrading.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    // Getters and Setters for the fields
    private int statusCode;
    private String message;
    private String details;
}