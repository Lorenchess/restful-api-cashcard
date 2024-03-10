package com.example.cashcard.mapper;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.entity.CashCard;
import org.springframework.stereotype.Component;

@Component
public class SimpleMapper {
    public static CashCardDTO entityToDTO(CashCard cashCard) {
        return new CashCardDTO(cashCard.getCashCardId(), cashCard.getAmount());
    }

    public static CashCard dtoToEntity(CashCardDTO cashCardDTO) {
        return new CashCard(cashCardDTO.getCashCardId(), cashCardDTO.getAmount());
    }

}
