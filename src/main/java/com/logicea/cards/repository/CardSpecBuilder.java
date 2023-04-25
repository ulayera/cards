package com.logicea.cards.repository;

import com.logicea.cards.model.Card;
import com.logicea.cards.model.Status;
import com.logicea.cards.model.User;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

public class CardSpecBuilder {

  private String name;
  private String color;
  private Status status;
  private LocalDateTime from;
  private LocalDateTime to;

  private User user;

  public CardSpecBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CardSpecBuilder color(String color) {
    this.color = color;
    return this;
  }

  public CardSpecBuilder status(Status status) {
    this.status = status;
    return this;
  }

  public CardSpecBuilder from(LocalDateTime from) {
    this.from = from;
    return this;
  }

  public CardSpecBuilder to(LocalDateTime to) {
    this.to = to;
    return this;
  }

  public CardSpecBuilder user(User user) {
    this.user = user;
    return this;
  }

  public Specification<Card> build() {
    return (root, query, builder) -> {
      var predicates = builder.conjunction();
      if (name != null) {
        predicates = builder.and(predicates, builder.equal(root.get("name"), name));
      }
      if (color != null) {
        predicates = builder.and(predicates, builder.equal(root.get("color"), color));
      }
      if (status != null) {
        predicates = builder.and(predicates, builder.equal(root.get("status"), status));
      }
      if (from != null) {
        predicates = builder.and(predicates, builder.greaterThanOrEqualTo(root.get("createdAt"), from));
      }
      if (to != null) {
        predicates = builder.and(predicates, builder.lessThanOrEqualTo(root.get("createdAt"), to));
      }
      if (user != null) {
        predicates = builder.and(predicates, builder.equal(root.get("user"), user));
      }
      return predicates;
    };
  }
}