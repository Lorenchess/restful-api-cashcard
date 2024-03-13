package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.exception.PrincipalForbiddenException;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface CashCardService {

    CashCardDTO findCashCardByIdAndOwner(Long id, Principal owner) throws CashCardNotFoundException;

    CashCardDTO saveCashCard(CashCardDTO cashCardDTO, Principal owner) throws PrincipalForbiddenException;

    List<CashCardDTO> findAllCashCards(Principal owner, Pageable pageable);

    void updateCashCard(Long requestId, CashCardDTO cashCardDTO, Principal principal) throws PrincipalForbiddenException;

}
