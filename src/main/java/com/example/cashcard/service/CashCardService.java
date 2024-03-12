package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.exception.CashCardNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CashCardService {

    CashCardDTO findCashCardById(Long id) throws CashCardNotFoundException;

    CashCardDTO saveCashCard(CashCardDTO cashCardDTO);

    List<CashCardDTO> findAllCashCards(Pageable pageable);
}
