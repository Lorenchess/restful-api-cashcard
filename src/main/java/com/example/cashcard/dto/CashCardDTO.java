package com.example.cashcard.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
@Getter
@EqualsAndHashCode
@Builder
public class CashCardDTO {
    private final Long cashCardId;
    private final Double amount;
    private final String owner;
}
