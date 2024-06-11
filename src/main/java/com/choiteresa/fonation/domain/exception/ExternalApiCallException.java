package com.choiteresa.fonation.domain.exception;


public class ExternalApiCallException extends RuntimeException{
    private final String message = "Not received response from external api";
}
