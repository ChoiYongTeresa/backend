package com.choiteresa.fonation.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException{
    private final String message = "Product not found";
}
