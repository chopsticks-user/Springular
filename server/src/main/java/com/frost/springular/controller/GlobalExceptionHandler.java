package com.frost.springular.controller;

import com.frost.springular.object.exception.CalendarEventException;
import com.frost.springular.object.exception.DuplicatedEmailException;
import com.frost.springular.object.exception.RefreshTokenExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ CalendarEventException.class })
  public ProblemDetail handleCalendarEventException(
      CalendarEventException exception) {
    exception.printStackTrace();

    return createHttpProblemDetail(
        exception.getStatusCode(), exception, exception.getReason());
  }

  @ExceptionHandler({
      DateTimeParseException.class,
      RefreshTokenExpiredException.class,
      DuplicatedEmailException.class,
      BadCredentialsException.class,
      ExpiredJwtException.class,
      SignatureException.class,
      AccessDeniedException.class,
      AccountStatusException.class,
      HttpMessageConversionException.class,
      SecurityException.class,
  })
  public ProblemDetail handleSecurityException(Exception exception) {
    exception.printStackTrace();

    return switch (exception) {
      case DateTimeParseException e ->
        createHttpProblemDetail(
            HttpStatus.BAD_REQUEST, e,
            "Invalid date format");
      case RefreshTokenExpiredException e ->
        createHttpProblemDetail(
            HttpStatus.UNAUTHORIZED, e,
            "Refresh token expired");
      case DuplicatedEmailException e ->
        createHttpProblemDetail(
            HttpStatus.CONFLICT, e,
            "Email address already in use");
      case BadCredentialsException e ->
        createHttpProblemDetail(
            HttpStatus.UNAUTHORIZED, e,
            "Email or password is incorrect");
      case ExpiredJwtException e ->
        createHttpProblemDetail(
            HttpStatus.UNAUTHORIZED, e,
            "JWT token has expired");
      case SignatureException e ->
        createHttpProblemDetail(
            HttpStatus.FORBIDDEN, e,
            "Invalid JWT signature");
      case AccessDeniedException e ->
        createHttpProblemDetail(
            HttpStatus.FORBIDDEN, e,
            "You are not authorized to access this resource");
      case AccountStatusException e ->
        createHttpProblemDetail(
            HttpStatus.FORBIDDEN, e, "Your account is locked");
      case HttpMessageConversionException e ->
        createHttpProblemDetail(HttpStatus.BAD_REQUEST, e,
            "Invalid http request format");
      default -> createUnknownServerErrorDetail(exception);
    };
  }

  @ExceptionHandler({ Exception.class })
  public ProblemDetail handleUnknownException(
      CalendarEventException exception) {
    exception.printStackTrace();

    return switch (exception) {
      case CalendarEventException e ->
        createHttpProblemDetail(
            exception.getStatusCode(), exception, exception.getMessage());
    };
  }

  private static ProblemDetail createHttpProblemDetail(
      int statusCode, Exception exception, String description) {
    return createHttpProblemDetail(
        HttpStatusCode.valueOf(statusCode), exception, description);
  }

  private static ProblemDetail createHttpProblemDetail(
      HttpStatusCode statusCode, Exception exception, String description) {
    ProblemDetail detail = ProblemDetail.forStatusAndDetail(
        statusCode, exception.getMessage());
    detail.setProperty("description", description);
    return detail;
  }

  private static ProblemDetail createUnknownServerErrorDetail(Exception exception) {
    return createHttpProblemDetail(
        400,
        exception,
        "Invalid http request format");
  }
}
