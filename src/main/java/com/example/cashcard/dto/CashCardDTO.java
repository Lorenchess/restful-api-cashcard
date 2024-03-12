package com.example.cashcard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class CashCardDTO {
    private final Long cashCardId;

    @Min(1)
    @Max(5000)
    private final Double amount;

    @NotNull(message = "Owner value should be set.")
    private final String owner;
}
