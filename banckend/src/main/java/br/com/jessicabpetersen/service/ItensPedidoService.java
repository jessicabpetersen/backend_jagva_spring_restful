package br.com.jessicabpetersen.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jessicabpetersen.repository.ItensPedidoRepository;

@Service
public class ItensPedidoService {
	
	@Autowired
	private ItensPedidoRepository repository;
	
	
	public boolean checkProdutoServicoById(UUID id) {
	//	var entity = repository.findByIdProdutoServico(id);
		
		return true;
	}
	

}
