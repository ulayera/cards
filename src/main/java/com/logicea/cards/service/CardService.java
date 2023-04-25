package com.logicea.cards.service;

import com.logicea.cards.model.Card;
import com.logicea.cards.model.User;
import com.logicea.cards.repository.CardRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  @Autowired
  private CardRepository cardRepository;

  public Page<Card> findByUser(User user, Pageable pageable) {
    return cardRepository.findByUser(user, pageable);
  }

  public Optional<Card> findByIdAndUser(Long id, User user) {
    return cardRepository.findByIdAndUser(id, user);
  }

  public Card save(Card card) {
    return cardRepository.save(card);
  }

  public void delete(Card card) {
    cardRepository.delete(card);
  }

  public Page<Card> findByFilters(String name, String color, String status, User user,
      Pageable pageable) {
    return cardRepository.findByFilters(name, color, status, user, pageable);
  }
}
