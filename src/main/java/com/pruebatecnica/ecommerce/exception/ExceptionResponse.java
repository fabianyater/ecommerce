package com.pruebatecnica.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private final List<String> messages;
    private final int statusCode;
    private final LocalDateTime timestamp;
}
