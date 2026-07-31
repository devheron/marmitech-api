package com.marmitech.Marmitech.Mapper.ResponseMapper;

import com.marmitech.Marmitech.DTO.ResponseDTO.PedidoItemResponseDTO;
import com.marmitech.Marmitech.Entity.PedidoItem;

public class ProdutoItemResponseMapper {
    public static PedidoItemResponseDTO toDto(PedidoItem pedidoItem){
        return new PedidoItemResponseDTO(
                pedidoItem.getId(),
                pedidoItem.getPedido().getId(),
                pedidoItem.getProduto().getId(),
                pedidoItem.getPedido().getCliente().getNome(),
                pedidoItem.getProduto().getNome(),
                pedidoItem.getQuantidade(),
                pedidoItem.getPrecoUnitarioPedido(),
                pedidoItem.getSubtotal()
        );
    }
}
