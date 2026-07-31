package com.marmitech.Marmitech.Mapper.ResponseMapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.marmitech.Marmitech.DTO.ResponseDTO.PedidoItemResponseDTO;
import com.marmitech.Marmitech.DTO.ResponseDTO.PedidoResponseDTO;
import com.marmitech.Marmitech.Entity.Pedido;
import com.marmitech.Marmitech.Entity.PedidoItem;

public class PedidoResponseMapper {
    private static PedidoItemResponseDTO toItemDto(PedidoItem pedidoItem){
        return new PedidoItemResponseDTO(
                pedidoItem.getId(),
                pedidoItem.getPedido().getId(),
                pedidoItem.getProduto().getId(),
                pedidoItem.getPedido().getCliente().getNome(),
                pedidoItem.getProduto().getNome(),
                pedidoItem.getQuantidade(),
                pedidoItem.getPrecoUnitarioPedido(),
                pedidoItem.getPrecoUnitarioPedido());
    }

    public static PedidoResponseDTO toDto(Pedido pedido){
        Set<PedidoItemResponseDTO> pedidoItemResponseDTOs = pedido.getPedidoItems()
                .stream()
                .map(PedidoResponseMapper::toItemDto)
                .collect(Collectors.toSet());

        //Passando o nome do cliente para nao dar erro na hora de enviar para o front
        String nomeCliente = (pedido.getCliente() != null) ? pedido.getCliente().getNome() : "Cliente não informado";

        return new PedidoResponseDTO(
                pedido.getId(),
                nomeCliente,
                pedido.getStatus(),
                pedido.getEnderecoEntrega(),
                pedidoItemResponseDTOs,
                pedido.getValorTotal(),
                pedido.getDataPedido()
        );
    }
}
