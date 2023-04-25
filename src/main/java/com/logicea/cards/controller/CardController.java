package com.logicea.cards.controller;

import com.logicea.cards.model.Card;
import com.logicea.cards.model.Status;
import com.logicea.cards.model.User;
import com.logicea.cards.repository.CardSpecBuilder;
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
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<Page<Card>> getAllCards(Principal principal, Pageable pageable,
      // a user can filter by name, color, status and a date time range
      // TODO: add sorting
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String color,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) LocalDateTime from,
      @RequestParam(required = false) LocalDateTime to) {
    User user = getUser(principal);
    CardSpecBuilder specBuilder = new CardSpecBuilder().name(name)
        .color(color)
        .status(Status.fromString(status))
        .from(from)
        .to(to);
    Page<Card> cards = cardService.findByUser(user, specBuilder, pageable);
    return ResponseEntity.ok(cards);
  }

  // users can search for a specific card by id
  @GetMapping("/{id}")
  public ResponseEntity<Card> getCardById(@PathVariable Long id, Principal principal) {
    User user = getUser(principal);
    Optional<Card> card = cardService.findByIdAndUser(id, user);
    return card.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.ofNullable(null));
  }

  // users can create a new card
  @PostMapping
  public ResponseEntity<Card> createCard(@Valid @RequestBody Card card, Principal principal) {
    User user = getUser(principal);
    card.setUser(user);
    // we set TO_DO as default status
    card.setStatus(Status.TO_DO);
    card.setDateCreated(LocalDateTime.now());
    Card createdCard = cardService.save(card);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(createdCard);
  }

  // users can update a card
  @PutMapping
  public ResponseEntity<Card> updateCard(@Valid @RequestBody Card updatedCard, Principal principal) {
    User user = getUser(principal);
    Optional<Card> existingCard = cardService.findByIdAndUser(updatedCard.getId(), user);
    if (existingCard.isPresent()) {
      Card card = existingCard.get();
      // Validate attributes are not null, so we don't clear unintentionally
      // If attribute is empty string, we want to clear it
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

  // users can delete a card
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
