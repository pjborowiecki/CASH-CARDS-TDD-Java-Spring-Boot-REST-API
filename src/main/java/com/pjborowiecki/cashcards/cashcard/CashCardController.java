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

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {

    private CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<CashCard>> findAll(@CurrentOwner String owner) {
        var result = this.cashCardRepository.findByOwner(owner);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
        return this.cashCardRepository.findById(requestedId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CashCard> createCashCard(
            @RequestBody CashCardRequest createCashCardRequest,
            @CurrentOwner String owner,
            UriComponentsBuilder ucb) {
        CashCard newCashCard = this.cashCardRepository.save(new CashCard(createCashCardRequest.amount(), owner));
        URI newCashCardLocation = ucb.path("/api/v1/cashcards/{id}").buildAndExpand(newCashCard.id()).toUri();
        return ResponseEntity.created(newCashCardLocation).body(newCashCard);
    }
}
