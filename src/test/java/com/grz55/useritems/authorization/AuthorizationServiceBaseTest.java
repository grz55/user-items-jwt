package com.grz55.useritems.authorization;

import com.grz55.useritems.mapper.UserMapper;
import com.grz55.useritems.repository.UserRepository;
import com.grz55.useritems.security.JWTUtil;
import com.grz55.useritems.service.IAuthorizationService;
import com.grz55.useritems.service.impl.AuthorizationServiceImpl;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

abstract class AuthorizationServiceBaseTest {

  @MockitoBean protected UserRepository userRepository = Mockito.mock(UserRepository.class);

  @MockitoBean protected PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

  @MockitoBean protected JWTUtil jwtUtil = Mockito.mock(JWTUtil.class);

  protected UserMapper userMapper = new UserMapper();

  protected IAuthorizationService authorizationService =
      new AuthorizationServiceImpl(userRepository, passwordEncoder, userMapper, jwtUtil);
}
