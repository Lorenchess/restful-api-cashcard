package com.example.cashcard.controller;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.exception.PrincipalForbiddenException;
import com.example.cashcard.service.CashCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.security.Principal;
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
    public ResponseEntity<CashCardDTO> findById(@PathVariable Long id, Principal owner) throws CashCardNotFoundException {
        CashCardDTO cashCardDTO = service.findCashCardByIdAndOwner(id, owner);

        return ResponseEntity.ok(cashCardDTO);

    }

    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody @Valid CashCardDTO cashCardDTO, Principal owner) throws PrincipalForbiddenException {
        CashCardDTO cardDTO = service.saveCashCard(cashCardDTO, owner);
        return entityWithLocation(cardDTO.getCashCardId());
    }

    @GetMapping
    public ResponseEntity<List<CashCardDTO>> getAllCashCards(Pageable pageable, Principal owner) {
        List<CashCardDTO> allCashCards = service.findAllCashCards(owner, pageable);
        return ResponseEntity.ok(allCashCards);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Void> putCashCard(@PathVariable Long requestId,
                                            @Valid @RequestBody CashCardDTO cashCardDTO,
                                            Principal principal) throws PrincipalForbiddenException {
       service.updateCashCard(requestId, cashCardDTO, principal);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteCashCard(@PathVariable Long requestId, Principal principal) throws PrincipalForbiddenException, CashCardNotFoundException {
        service.deleteCard(requestId, principal);
        return ResponseEntity.noContent().build();
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
