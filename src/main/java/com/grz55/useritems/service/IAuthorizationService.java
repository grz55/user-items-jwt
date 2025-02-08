package com.grz55.useritems.service;

import com.grz55.useritems.dto.*;

public interface IAuthorizationService {

  void register(RegistrationRequestDTO registrationRequest);

  LoginResponseDTO login(LoginRequestDTO loginRequest);
}
