package com.grz55.useritems.exception;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.violations.Violation;

@Getter
public class ConstraintValidationProblem implements Problem {

  private final StatusType status;
  private final List<Violation> violations;

  public ConstraintValidationProblem(final StatusType status, final List<Violation> violations) {
    this.status = status;
    this.violations =
        violations != null ? Collections.unmodifiableList(violations) : Collections.emptyList();
  }

  @Override
  public String getTitle() {
    return "Constraint Violation";
  }
}
