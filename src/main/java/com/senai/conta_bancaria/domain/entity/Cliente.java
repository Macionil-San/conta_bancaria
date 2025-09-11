package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    private Long cpf;

    @OneToMany(mappedBy = "contas")
    private List<Conta> contas;
}
