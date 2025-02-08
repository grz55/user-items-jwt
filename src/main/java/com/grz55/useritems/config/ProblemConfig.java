package com.grz55.useritems.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

@Configuration
public class ProblemConfig {
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().registerModules(new ProblemModule());
  }
}
