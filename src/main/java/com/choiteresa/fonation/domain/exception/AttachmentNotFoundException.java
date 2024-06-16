package com.choiteresa.fonation.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttachmentNotFoundException extends RuntimeException{
    private final String message = "Attachment not found";
}
