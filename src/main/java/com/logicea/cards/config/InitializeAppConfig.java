package com.logicea.cards.config;

import com.logicea.cards.model.User;
import com.logicea.cards.security.JwtService;
import com.logicea.cards.service.UserService;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class InitializeAppConfig {

  @Value("${test.member.a}")
  private String testMemberA;
  @Value("${test.member.b}")
  private String testMemberB;
  @Value("${test.admin}")
  private String testAdmin;
  private final JwtService jwtService;
  private final UserService userService;

  public InitializeAppConfig(JwtService jwtService, UserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void printTestToken() {
    Arrays.asList(testMemberA, testMemberB, testAdmin)
        .forEach(this::initialize);
  }

  private void initialize(String email) {
    Optional<User> user = userService.findByEmail(email);
    if (user.isEmpty()) {
      userService.save(User.withEmail(email));
    }
    String token = this.jwtService.generateToken(email);
    System.out.println("\nTest token for " + email + ":");
    System.out.println("Bearer " + token);
  }

}
