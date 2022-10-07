package br.com.jessicabpetersen.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.jessicabpetersen.model.Pedido;

public interface PedidoPaginationRepository extends PagingAndSortingRepository<Pedido, UUID>{

}
