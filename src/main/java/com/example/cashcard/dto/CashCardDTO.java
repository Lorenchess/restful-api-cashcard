package com.example.cashcard.dto;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class CashCardDTO {
    private final Long cashCardId;
    private final Double amount;
}
