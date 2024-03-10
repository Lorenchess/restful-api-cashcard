package com.example.cashcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CASHCARD_ID")
    private Long cashCardId;

    private Double amount;
}
