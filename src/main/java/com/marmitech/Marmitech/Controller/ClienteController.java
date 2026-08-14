package com.marmitech.Marmitech.Controller;

import com.marmitech.Marmitech.DTO.RequestDTO.ClienteRequestDTO;
import com.marmitech.Marmitech.DTO.ResponseDTO.ClienteResponseDTO;
import com.marmitech.Marmitech.Services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private static final String ROLE_ADMIN = "hasRole('ADMIN')";
    private static final String ROLE_ADMIN_FUNCIONARIO = "hasAnyRole('ADMIN','FUNCIONARIO')";

    private final ClienteService clienteService;

    @PreAuthorize(ROLE_ADMIN)
    @PostMapping("/save")
    public ResponseEntity<ClienteResponseDTO> save(@RequestBody @Valid ClienteRequestDTO dto) {
        var result = clienteService.save(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize(ROLE_ADMIN_FUNCIONARIO)
    @GetMapping("/findAll")
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        var result = clienteService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize(ROLE_ADMIN_FUNCIONARIO)
    @GetMapping("/findById/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Integer id) {
        var result = clienteService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize(ROLE_ADMIN)
    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Integer id, @RequestBody ClienteRequestDTO dto) {
        var result = clienteService.update(id, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize(ROLE_ADMIN)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize(ROLE_ADMIN_FUNCIONARIO)
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<ClienteResponseDTO>> findByNome(@PathVariable String nome) {
        var result = clienteService.findByNome(nome);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize(ROLE_ADMIN_FUNCIONARIO)
    @GetMapping("/findByCpfCnpj/{cpf_cnpj}")
    public ResponseEntity<ClienteResponseDTO> findByCpfCnpj(@PathVariable String cpf_cnpj) {
        var result = clienteService.findByCpfCnpj(cpf_cnpj);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}