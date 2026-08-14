package com.marmitech.Marmitech.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marmitech.Marmitech.Entity.Pedido;
import com.marmitech.Marmitech.Entity.Produto;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

<<<<<<< Updated upstream
    public List<Pedido> findByPedidoItemsProduto(Produto produto);

    public List<Pedido> findByPedidoItemsProdutoNome(String nome);
=======
     List<Pedido> findByPedidoItemsProduto(Produto produto);     

     List<Pedido> findByPedidoItemsProdutoNome(String nome); 
>>>>>>> Stashed changes

     List<Pedido> findByStatus(String status);

    List<Pedido> findByClienteEmail(String email);

}
