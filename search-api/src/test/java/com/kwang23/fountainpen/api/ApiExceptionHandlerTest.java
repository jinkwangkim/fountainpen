package com.kwang23.fountainpen.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionHandlerTest {
    ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException e = new IllegalArgumentException();
        ResponseEntity<ErrorResponse> responseEntity = apiExceptionHandler.handleIllegalArgumentException(e);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void handleHttpClientErrorException() {
        HttpClientErrorException e = HttpClientErrorException.create("", HttpStatus.UNAUTHORIZED, "", new HttpHeaders(), null, null);
        ResponseEntity<ErrorResponse> responseEntity = apiExceptionHandler.handleHttpClientErrorException(e);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}