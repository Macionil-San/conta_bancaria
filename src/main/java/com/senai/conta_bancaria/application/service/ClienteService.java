package com.senai.conta_bancaria.application.service;

import com.senai.conta_bancaria.application.dto.ClienteDTO;
import com.senai.conta_bancaria.application.dto.ClienteReponseDTO;
import com.senai.conta_bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteReponseDTO regitrarCliente(ClienteDTO dto) {                    //salva cliente e conta

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> repository.save(dto.toeEntity())
        );
        var contas = cliente.getContas();
        var novaConta = dto.conta().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(Conta c -> c.getClass().equals(dto.conta().getClass()) && c.isAtiva());


        return
    }
}
