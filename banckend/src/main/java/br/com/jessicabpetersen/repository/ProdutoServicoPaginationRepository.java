package br.com.jessicabpetersen.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.jessicabpetersen.model.ProdutoServico;

@Repository
public interface ProdutoServicoPaginationRepository extends PagingAndSortingRepository<ProdutoServico, UUID>, JpaSpecificationExecutor<ProdutoServico>{

}
