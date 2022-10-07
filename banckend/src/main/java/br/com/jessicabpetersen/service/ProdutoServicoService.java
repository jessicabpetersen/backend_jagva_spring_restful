package br.com.jessicabpetersen.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.jessicabpetersen.exception.ResourceNotFoundException;
import br.com.jessicabpetersen.exception.UnableToDeleteException;
import br.com.jessicabpetersen.model.ProdutoServico;
import br.com.jessicabpetersen.modelVO.BuscaProdutoServico;
import br.com.jessicabpetersen.modelVO.ProdutoServicoVo;
import br.com.jessicabpetersen.repository.ItensPedidoRepository;
import br.com.jessicabpetersen.repository.ProdutoServicoPaginationRepository;
import br.com.jessicabpetersen.repository.ProdutoServicoRepository;

@Service
public class ProdutoServicoService {
	

	@Autowired
	private ProdutoServicoRepository repository;
	
	@Autowired
	private ItensPedidoRepository itensRepository;
	
	@Autowired
	private ProdutoServicoPaginationRepository repositoryPagination;
	
	@Autowired 
	private ModelMapper mapper;
	

	public ProdutoServicoVo save(ProdutoServicoVo vo) {
		var ps = mapper.map(vo, ProdutoServico.class);
		var entity = repository.save(ps);
		return  mapper.map(entity, ProdutoServicoVo.class);
	}

	public ProdutoServicoVo update(ProdutoServicoVo vo) {
		var retorno = repository.findById(vo.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		update(retorno, mapper.map(vo, ProdutoServico.class));
		var entity = repository.save(retorno);
		return  mapper.map(entity, ProdutoServicoVo.class);
	}
	
	protected ProdutoServico update(ProdutoServico banco, ProdutoServico update) {
		if(update.getDescricao() != null) {
			banco.setDescricao(update.getDescricao());
		}
		if(update.getNome() != null) {
			banco.setNome(update.getNome());
		}
		if(update.getTipo() != null) {
			banco.setTipo(update.getTipo());
		}
		if(update.getValor() != null) {
			banco.setValor(update.getValor());
		}
		return banco;
	}

	public ProdutoServicoVo findById(String id) {
		UUID uuid = UUID.fromString(id);
		Optional<ProdutoServico> produtoServico = repository.findById(uuid);
		if(produtoServico.isEmpty()) {
			throw new ResourceNotFoundException("No records found for this ID");
		}
		return mapper.map(produtoServico.get(), ProdutoServicoVo.class);
		
	}

	public List<ProdutoServicoVo> findAll() {
		return repository.findAll().stream().map(p -> mapper.map(p, ProdutoServicoVo.class)).collect(Collectors.toList());
	}

	public void deleteById(String id) {
		UUID uuid = UUID.fromString(id);
		var retorno = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	
		var itens = itensRepository.findByIdProdutoServico(retorno);
		if(itens.get().size() == 0) {
			repository.deleteById(uuid);
			return;
		}
		
		throw new UnableToDeleteException("Unable to Delete this record, it is related to an order");
	}
	
	public Page<ProdutoServicoVo> search(String searchTerm, int page, int size){
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<ProdutoServico> pageEntity = repository.search(searchTerm, pageRequest);
		Page<ProdutoServicoVo> po = pageEntity.map(obj -> mapper.map(obj, ProdutoServicoVo.class));  
		return po;
	}

	public Page<ProdutoServicoVo> getPatination(Pageable pageable) {
		Page<ProdutoServico> pageP = repositoryPagination.findAll(pageable);
		Page<ProdutoServicoVo> po = pageP.map(obj -> mapper.map(obj, ProdutoServicoVo.class));  
		return po;
	}

	public Page<ProdutoServicoVo> getPaginationFilter(Pageable pageable, BuscaProdutoServico busca) {
		Page<ProdutoServico> pageP = repositoryPagination.findAll(busca.toSpec(), pageable);
		Page<ProdutoServicoVo> po = pageP.map(obj -> mapper.map(obj, ProdutoServicoVo.class));  
		return po;
	}

}
