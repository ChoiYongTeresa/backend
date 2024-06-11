package com.choiteresa.fonation.domain.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FonationHttpErrorResponseDto {
    private int status;
    private String message;
}
