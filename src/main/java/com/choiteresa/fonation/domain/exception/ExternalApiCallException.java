package com.choiteresa.fonation.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalApiCallException extends RuntimeException{
    private final String message = "Not received response from external api";
}
