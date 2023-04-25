package com.logicea.cards.controller;

import com.logicea.cards.model.User;
import com.logicea.cards.service.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<User> login(@Valid @RequestBody User user) {
    Optional<User> existingUser = userService.findByEmail(user.getEmail());
    if (existingUser.isPresent() && user.getPassword()
        .equals(existingUser.get()
            .getPassword())) {
      return ResponseEntity.ok(existingUser.get());
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .build();
  }
}
