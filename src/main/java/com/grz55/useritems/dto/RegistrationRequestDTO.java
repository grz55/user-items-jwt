package com.grz55.useritems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

  @NotBlank
  @Size(min = 6)
  private String login;

  @NotBlank
  @Size(min = 6)
  private String password;
}
