package br.com.jessicabpetersen.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jessicabpetersen.model.ProdutoServico;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID>, JpaSpecificationExecutor<ProdutoServico>{
	
	@Query("FROM ProdutoServico ps "+
			"WHERE ps.nome like %:searchTerm% ")
	Page<ProdutoServico> search(@Param("searchTerm") String searchTerm, 
								Pageable pageable);

}
