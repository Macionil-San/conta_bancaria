package com.senai.conta_bancaria.application.dto;

import java.util.List;

public record ClienteReponseDTO(

        String id,

        String nome,

        String cpf,

        List<ContaDTO> contas
) {
}
