package com.marmitech.Marmitech.DTO.RequestDTO;

import java.math.BigDecimal;

public record PedidoItemRequestDTO(int id, int pedidoId, int produtoId, int quantidade, Double precoUnitarioPedido) {

}
