package com.example.cashcard.controller;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.repository.CashCardRepository;
import com.example.cashcard.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {


    private final CashCardService service;

    @Autowired
    public CashCardController(CashCardService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCardDTO> findById(@PathVariable Long id) throws CashCardNotFoundException {
        CashCardDTO cashCardDTO = service.findCashCardById(id);

        return ResponseEntity.ok(cashCardDTO);

    }

    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody CashCardDTO cashCardDTO){
        CashCardDTO cardDTO = service.saveCashCard(cashCardDTO);
        return entityWithLocation(cardDTO.getCashCardId());
    }

    @GetMapping
    public ResponseEntity<List<CashCardDTO>> getAllCashCards(Pageable pageable) {
        List<CashCardDTO> allCashCards = service.findAllCashCards(pageable);
        return ResponseEntity.ok(allCashCards);
    }

    private ResponseEntity<Void> entityWithLocation(Object resourceId) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(resourceId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
