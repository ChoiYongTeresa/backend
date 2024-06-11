package com.choiteresa.fonation.domain.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AttachmentNotFoundException.class)
    protected FonationHttpErrorResponseDto handleAttachmentNotFoundException(AttachmentNotFoundException ex){
        return FonationHttpErrorResponseDto.builder()
                .status(FonationHttpErrorCode.NOT_FOUND.getStatus())
                .message(ex.getMessage())
                .build();
    }
    @ExceptionHandler(ExternalApiCallException.class)
    protected FonationHttpErrorResponseDto handleExternalApiCallException(ExternalApiCallException ex){
        return FonationHttpErrorResponseDto.builder()
                .status(FonationHttpErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(RestClientException.class)
    protected FonationHttpErrorResponseDto handleExternalApiCallException(RestClientException ex){
        return FonationHttpErrorResponseDto.builder()
                .status(FonationHttpErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .message(ex.getMessage())
                .build();
    }
}
