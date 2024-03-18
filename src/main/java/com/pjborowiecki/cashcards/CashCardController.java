package com.pjborowiecki.cashcards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/cashcards")
public class CashCardController {

    @GetMapping("/{requestedId}")
    private ResponseEntity<String> findById() {
        return ResponseEntity.ok("{}");
    }

}
