package com.frost.springular.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        exception.printStackTrace();

        return switch (exception) {
            case JwtRefreshTokenExpiredException e ->
                createHttpProblemDetail(401, exception, "Refresh token expired");
            case DuplicatedEmailException e ->
                createHttpProblemDetail(409, exception, "Email address already in use");
            case BadCredentialsException e ->
                createHttpProblemDetail(401, exception, "Email or password is incorrect");
            case ExpiredJwtException e ->
                createHttpProblemDetail(403, exception, "JWT token has expired");
            case SignatureException e ->
                createHttpProblemDetail(403, exception, "Invalid JWT signature");
            case AccessDeniedException e ->
                createHttpProblemDetail(403, exception, "You are not authorized to access this resource");
            case AccountStatusException e ->
                createHttpProblemDetail(403, exception, "Your account is locked");
            default -> createHttpProblemDetail(500, exception, "Unknown internal server error");
        };
    }

    private static ProblemDetail createHttpProblemDetail(int statusCode, Exception exception, String description) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(statusCode),
                exception.getMessage());
        detail.setProperty("description", description);
        return detail;
    }
}
