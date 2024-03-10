package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;

public interface CashCardService {

    CashCardDTO findCashCardById(Long id);
}
