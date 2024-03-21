package com.pjborowiecki.cashcards.cashcard;

import java.net.URI;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {

    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<CashCard>> findAll(Authentication authentication) {
        var filteredCashCards = new ArrayList<CashCard>();
        this.cashCardRepository.findAll().forEach(cashCard -> {
            if (cashCard.owner().equals(authentication.getName())) {
                filteredCashCards.add(cashCard);
            }
        });
        return ResponseEntity.ok(filteredCashCards);
    }

    @GetMapping("{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        return this.cashCardRepository.findById(requestedId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<CashCard> createCashCard(@RequestBody CashCard newCashCardRequest,
            UriComponentsBuilder ucb) {
        CashCard newCashCard = cashCardRepository.save(newCashCardRequest);
        URI newCashCardLocation = ucb.path("/api/v1/cashcards/{id}").buildAndExpand(newCashCard.id()).toUri();
        return ResponseEntity.created(newCashCardLocation).body(newCashCard);
    }
}
