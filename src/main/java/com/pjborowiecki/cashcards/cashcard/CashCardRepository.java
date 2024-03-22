package com.pjborowiecki.cashcards.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {

    Iterable<CashCard> findByOwner(String owner);

    default Iterable<CashCard> findAll() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String owner = authentication.getName();
        return findByOwner(owner);
    }

}