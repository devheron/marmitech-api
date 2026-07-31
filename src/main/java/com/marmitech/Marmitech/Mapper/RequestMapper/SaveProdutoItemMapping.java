package com.marmitech.Marmitech.Mapper.RequestMapper;

import com.marmitech.Marmitech.DTO.RequestDTO.PedidoItemRequestDTO;
import com.marmitech.Marmitech.DTO.ResponseDTO.PedidoItemResponseDTO;
import com.marmitech.Marmitech.Entity.PedidoItem;

public class SaveProdutoItemMapping {
    public static PedidoItemRequestDTO toDto(PedidoItemResponseDTO produtoItem){
        return new PedidoItemRequestDTO(
            produtoItem.id(),
            produtoItem.pedidoId(),
            produtoItem.produtoId(),
            produtoItem.quantidade(),
            produtoItem.precoUnitarioPedido()
        );
    }
}
