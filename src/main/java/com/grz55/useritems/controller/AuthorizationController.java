package com.grz55.useritems.controller;

import com.grz55.useritems.dto.LoginRequestDTO;
import com.grz55.useritems.dto.LoginResponseDTO;
import com.grz55.useritems.dto.RegistrationRequestDTO;
import com.grz55.useritems.service.IAuthorizationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthorizationController {

  private final IAuthorizationService authorizationService;

  @PostMapping("/register")
  public ResponseEntity<Void> register(
      @Valid @RequestBody RegistrationRequestDTO registrationRequest) {
    authorizationService.register(registrationRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
    LoginResponseDTO loginResponse = authorizationService.login(loginRequest);
    return ResponseEntity.ok(loginResponse);
  }
}
