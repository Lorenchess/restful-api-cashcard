package com.example.cashcard.mapper;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.entity.CashCard;
import org.springframework.stereotype.Component;

@Component
public class SimpleMapper {
    public CashCardDTO entityToDTO(CashCard cashCard) {
        return CashCardDTO.builder()
                          .cashCardId(cashCard.getCashCardId())
                          .amount(cashCard.getAmount())
                          .build();
    }

    public CashCard dtoToEntity(CashCardDTO cashCardDTO) {
        return CashCard.builder()
                       .cashCardId(cashCardDTO.getCashCardId())
                       .amount(cashCardDTO.getAmount())
                       .build();
    }

}
