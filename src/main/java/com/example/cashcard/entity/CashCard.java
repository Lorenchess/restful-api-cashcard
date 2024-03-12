package com.example.cashcard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cashCardId;

    private Double amount;

    private String owner;
}
