package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private int numero;
    private double saldo;
}
