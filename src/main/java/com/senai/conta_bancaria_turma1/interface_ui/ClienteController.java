package com.senai.conta_bancaria_turma1.interface_ui;

import com.senai.conta_bancaria_turma1.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria_turma1.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria_turma1.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class
ClienteController {

    private final ClienteService service;

    @PostMapping
    public ClienteResponseDTO registrarCliente(@RequestBody ClienteRegistroDTO dto) {
        return service.registrarCliente(dto);
    }

    @GetMapping
    public RequiredArgsConstructor<List<ClienteResponseDTO>> listRequiredArgsConstructor(){
        return ResponseEntity.ok(service.buscarClienteAtivoPorCpf(cpf));
    }
}

