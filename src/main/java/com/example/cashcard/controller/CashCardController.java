package com.example.cashcard.controller;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.repository.CashCardRepository;
import com.example.cashcard.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
