package com.marmitech.Marmitech.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marmitech.Marmitech.DTO.RequestDTO.PedidoRequestDTO;
import com.marmitech.Marmitech.DTO.ResponseDTO.PedidoResponseDTO;
import com.marmitech.Marmitech.Entity.Pedido;
import com.marmitech.Marmitech.Mapper.ResponseMapper.PedidoResponseMapper;
import com.marmitech.Marmitech.Services.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/save")
    public ResponseEntity<PedidoResponseDTO> save(@RequestBody PedidoRequestDTO dto) {
        return new ResponseEntity<>( pedidoService.save( dto ), HttpStatus.CREATED );
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll() {
        var result = pedidoService.findAll();
        return new ResponseEntity<>( result, HttpStatus.OK );
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Integer id) {
        var result = pedidoService.findById( id );
        PedidoResponseDTO pedidoDto = PedidoResponseMapper.toDto( result );
        return new ResponseEntity<>( pedidoDto, HttpStatus.OK );
    }

    @GetMapping("/findByStatus")
    public ResponseEntity<List<Pedido>> findByStatus(@RequestParam String status) {
        List<Pedido> result = pedidoService.findByStatus( status );
        return new ResponseEntity<>( result, HttpStatus.OK );

    }

    @GetMapping("/findByProdutoNome")
    public ResponseEntity<List<Pedido>> findByProdutoNome(@RequestParam String nomeProduto) {
        List<Pedido> result = pedidoService.findByProdutoNome( nomeProduto );
        return new ResponseEntity<>( result, HttpStatus.OK );
    }

    @GetMapping("/findByProduto")
    public ResponseEntity<List<Pedido>> findByProduto(@RequestParam int produtoId) {
        List<Pedido> result = pedidoService.findByProduto( produtoId );
        return new ResponseEntity<>( result, HttpStatus.OK );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PedidoResponseDTO> update(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Pedido updatedPedido = pedidoService.update( id, pedido );
        PedidoResponseDTO pedidoDto = PedidoResponseMapper.toDto( updatedPedido );
        return new ResponseEntity<>( pedidoDto, HttpStatus.OK );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pedidoService.delete( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

}
