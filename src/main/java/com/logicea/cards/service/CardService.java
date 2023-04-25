package com.logicea.cards.service;

import com.logicea.cards.model.Card;
import com.logicea.cards.model.User;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.CardSpecBuilder;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private final CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public Page<Card> findByUser(User user, CardSpecBuilder specBuilder, Pageable pageable) {
    // if not an admin, only return cards for the current user
    if (!user.isAdmin()) {
      specBuilder.user(user);
    }
    Specification<Card> spec = specBuilder.build();
    return cardRepository.findAll(spec, pageable);
  }

  public Optional<Card> findByIdAndUser(Long id, User user) {
    // if not an admin, only return cards for the current user
    return user.isAdmin() ? cardRepository.findById(id) : cardRepository.findByIdAndUser(id, user);
  }

  public Card save(Card card) {
    return cardRepository.save(card);
  }

  public void delete(Card card) {
    cardRepository.delete(card);
  }


}
