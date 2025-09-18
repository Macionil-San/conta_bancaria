package com.senai.conta_bancaria.Interface_ui;

import com.senai.conta_bancaria.application.dto.ClienteDTO;
import com.senai.conta_bancaria.application.dto.ClienteReponseDTO;
import com.senai.conta_bancaria.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;


    @PostMapping
    public ClienteReponseDTO regitrarCliente(@RequestBody ClienteDTO dto) {
        return service.regitrarCliente(dto);
    }

}
