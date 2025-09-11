package com.senai.conta_bancaria.application.dto;

import com.senai.conta_bancaria.domain.entity.Cliente;
import com.senai.conta_bancaria.domain.entity.Conta;

import java.util.List;

public record ClienteDTO(
        String id,
        String nome,
        Long cpf,
        List contas
) {
    public static ClienteDTO fromEntity(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getContas() != null ? cliente.getContas(): null

        );
    }

    public Cliente toEntity(Conta conta) {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setContas(this.contas);
        return cliente;
    }
}
