package com.logicea.cards.controller;

import com.logicea.cards.model.Card;
import com.logicea.cards.model.User;
import com.logicea.cards.service.CardService;
import com.logicea.cards.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class CardController {

  private final CardService cardService;
  private final UserService userService;

  public CardController(CardService cardService, UserService userService) {
    this.cardService = cardService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<Page<Card>> getAllCards(Principal principal, Pageable pageable) {
    User user = getUser(principal);
    Page<Card> cards = cardService.findByUser(user, pageable);
    return ResponseEntity.ok(cards);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Card> getCardById(@PathVariable Long id, Principal principal) {
    User user = getUser(principal);
    Optional<Card> card = cardService.findByIdAndUser(id, user);
    return card.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ofNullable(null));
  }

  @PostMapping
  public ResponseEntity<Card> createCard(@Valid @RequestBody Card card, Principal principal) {
    User user = getUser(principal);
    card.setUser(user);
    card.setDateCreated(LocalDateTime.now());
    Card createdCard = cardService.save(card);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(createdCard);
  }

  @PutMapping
  public ResponseEntity<Card> updateCard(@Valid @RequestBody Card updatedCard, Principal principal) {
    User user = getUser(principal);
    Optional<Card> existingCard = cardService.findByIdAndUser(updatedCard.getId(), user);
    if (existingCard.isPresent()) {
      Card card = existingCard.get();
      if (updatedCard.getName() != null) {
        card.setName(updatedCard.getName());
      }
      if (updatedCard.getDescription() != null) {
        card.setDescription(updatedCard.getDescription());
      }
      if (updatedCard.getColor() != null) {
        card.setColor(updatedCard.getColor());
      }
      if (updatedCard.getStatus() != null) {
        card.setStatus(updatedCard.getStatus());
      }
      Card savedCard = cardService.save(card);
      return ResponseEntity.ok(savedCard);
    }
    return ResponseEntity.notFound()
        .build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCard(@PathVariable Long id, Principal principal) {
    User user = getUser(principal);
    Optional<Card> existingCard = cardService.findByIdAndUser(id, user);
    if (existingCard.isPresent()) {
      cardService.delete(existingCard.get());
      return ResponseEntity.noContent()
          .build();
    }
    return ResponseEntity.notFound()
        .build();
  }

  private User getUser(Principal principal) {
    return userService.findByEmail(principal.getName())
        .orElseThrow();
  }
}
