package com.senai.conta_bancaria.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder  //ajuda a contruir o abjeto sem precisar dos new bla bla bla,,


@Table(name = "cliente", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
}
)


public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false,length = 100)
    private String nome;

    @Column(nullable = false,length = 11)
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Conta> contas;

    @Column(nullable = false )
    private Boolean ativo;
}
