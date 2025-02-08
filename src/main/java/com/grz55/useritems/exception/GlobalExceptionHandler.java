package com.grz55.useritems.exception;

import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.Violation;

@RestControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling {

  @Override
  public ResponseEntity<Problem> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, NativeWebRequest request) {
    StatusType status = this.defaultConstraintViolationStatus();
    List<Violation> violations =
        this.createViolations(exception.getBindingResult()).stream()
            .sorted(Comparator.comparing(Violation::getField).thenComparing(Violation::getMessage))
            .toList();
    Problem problem = new ConstraintValidationProblem(status, violations);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
  }
}
