package br.com.jessicabpetersen.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jessicabpetersen.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID>{

	@Query("FROM Pedido p WHERE p.desconto in ( :searchTerm ) ")
	Page<Pedido> search(@Param("searchTerm") Double searchTerm, 
								Pageable pageable);
}
