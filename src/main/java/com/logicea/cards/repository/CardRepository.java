package com.logicea.cards.repository;

import com.logicea.cards.model.Card;
import com.logicea.cards.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

  Page<Card> findByUser(User user, Pageable pageable);

  Optional<Card> findByIdAndUser(Long id, User user);

  @Query("SELECT c FROM Card c WHERE (:name IS NULL OR c.name LIKE %:name%) AND " +
      "(:color IS NULL OR c.color = :color) AND " +
      "(:status IS NULL OR c.status = :status) AND " +
      "(:user IS NULL OR c.user = :user)")
  Page<Card> findByFilters(@Param("name") String name, @Param("color") String color,
      @Param("status") String status, @Param("user") User user, Pageable pageable);
}
