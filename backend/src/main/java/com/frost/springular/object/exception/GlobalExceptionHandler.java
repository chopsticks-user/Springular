package com.frost.springular.object.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.format.DateTimeParseException;

import org.springframework.http.converter.HttpMessageConversionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        exception.printStackTrace();

        return switch (exception) {
            case CustomRepeatIntervalException e ->
                createHttpProblemDetail(400, e, e.getMessage());
            case DateTimeParseException e ->
                createHttpProblemDetail(400, e, "Invalid date format");
            case MethodArgumentNotValidException e ->
                createHttpProblemDetail(400, e,
                        e.getBindingResult().getFieldError().getDefaultMessage());
            case JwtRefreshTokenExpiredException e ->
                createHttpProblemDetail(401, e, "Refresh token expired");
            case DuplicatedEmailException e ->
                createHttpProblemDetail(409, e, "Email address already in use");
            case BadCredentialsException e ->
                createHttpProblemDetail(401, e, "Email or password is incorrect");
            case ExpiredJwtException e ->
                createHttpProblemDetail(401, e, "JWT token has expired");
            case SignatureException e ->
                createHttpProblemDetail(403, e, "Invalid JWT signature");
            case AccessDeniedException e ->
                createHttpProblemDetail(403, e, "You are not authorized to access this resource");
            case AccountStatusException e ->
                createHttpProblemDetail(403, e, "Your account is locked");
            case HttpMessageConversionException e ->
                createHttpProblemDetail(400, e, "Invalid http request format");
            default -> createHttpProblemDetail(
                    500, exception, "Unknown internal server error");
        };
    }

    private static ProblemDetail createHttpProblemDetail(int statusCode, Exception exception, String description) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(statusCode), exception.getMessage());
        detail.setProperty("description", description);
        return detail;
    }
}
