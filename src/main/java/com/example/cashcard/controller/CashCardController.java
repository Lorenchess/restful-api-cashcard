package com.example.cashcard.controller;

import com.example.cashcard.dto.CashCardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    @GetMapping("/{id}")
    public ResponseEntity<CashCardDTO> findById(@PathVariable Long id) {
        if(id.equals(99L)) {
            CashCardDTO cashCardDTO = new CashCardDTO(99L, 123.45);
            return ResponseEntity.ok(cashCardDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
