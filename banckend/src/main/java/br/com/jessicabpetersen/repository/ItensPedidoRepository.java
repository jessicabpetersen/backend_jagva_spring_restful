package br.com.jessicabpetersen.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jessicabpetersen.model.ItensPedido;
import br.com.jessicabpetersen.model.Pedido;
import br.com.jessicabpetersen.model.ProdutoServico;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedido, UUID>{
	
	Optional<List<ItensPedido>> findByIdProdutoServico(ProdutoServico produto);
	
	Optional<List<ItensPedido>> findByIdPedido(Pedido pedido);
	
	Integer deleteByIdPedido(Pedido pedido);
	
}
