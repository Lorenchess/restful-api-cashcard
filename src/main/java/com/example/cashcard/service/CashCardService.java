package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.exception.CashCardNotFoundException;

public interface CashCardService {

    CashCardDTO findCashCardById(Long id) throws CashCardNotFoundException;
}
