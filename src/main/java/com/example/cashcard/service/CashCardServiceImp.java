package com.example.cashcard.service;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.entity.CashCard;
import com.example.cashcard.mapper.SimpleMapper;
import com.example.cashcard.repository.CashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashCardServiceImp implements CashCardService{

    private final CashCardRepository repository;
    @Autowired
    public CashCardServiceImp(CashCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public CashCardDTO findCashCardById(Long id) {
        CashCard cashCard = repository.findByCashCardId(id);
        return SimpleMapper.entityToDTO(cashCard);
    }


}
