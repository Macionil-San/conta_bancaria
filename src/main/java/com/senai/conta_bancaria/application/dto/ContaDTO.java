package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Cliente;
import com.senai.conta_bancaria.domain.entity.Conta;
import com.senai.conta_bancaria.domain.entity.ContaCorrente;
import com.senai.conta_bancaria.domain.entity.ContaPoupanca;

import java.math.BigDecimal;

public record ContaDTO(

        String numero,

        String tipo,

        BigDecimal saldo
) {

    public Conta toEntity(Cliente cliente) {
        if ("CORRENTE".equalsIgnoreCase(tipo)){
            return ContaCorrente.builder()
                    .cliente(cliente)
                    .numero(numero)
                    .saldo(saldo)
                    .ativa(true)
                    .build();
        } else if ("POUPANÇA".equalsIgnoreCase(tipo)) {
            return ContaPoupanca.builder()
                    .cliente(cliente)
                    .numero(numero)
                    .saldo(saldo)
                    .ativa(true)
                    .build();

        }

        return null;
    }
}
