package com.kwang23.fountainpen.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final String errorMessage;
    private final int statusCode;
}
