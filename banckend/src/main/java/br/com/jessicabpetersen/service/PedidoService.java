package br.com.jessicabpetersen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jessicabpetersen.exception.ResourceNotFoundException;
import br.com.jessicabpetersen.model.ItensPedido;
import br.com.jessicabpetersen.model.Pedido;
import br.com.jessicabpetersen.model.ProdutoServico;
import br.com.jessicabpetersen.modelVO.PedidoVO;
import br.com.jessicabpetersen.modelVO.ProdutoServicoVo;
import br.com.jessicabpetersen.modelVO.Status;
import br.com.jessicabpetersen.modelVO.Tipo;
import br.com.jessicabpetersen.repository.ItensPedidoRepository;
import br.com.jessicabpetersen.repository.PedidoPaginationRepository;
import br.com.jessicabpetersen.repository.PedidoRepository;
import br.com.jessicabpetersen.repository.ProdutoServicoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PedidoPaginationRepository repositoryPagination;
	
	@Autowired
	private ItensPedidoRepository itensRepository;
	
	@Autowired
	private ProdutoServicoRepository produtoRepository;
	
	@Autowired 
	private ModelMapper mapper;

	@Transactional
	public PedidoVO save(PedidoVO vo) {
		PedidoVO voReturn = new PedidoVO();
		
		Pedido pedido = new Pedido();
		pedido.setDesconto(vo.getDesconto());
		pedido.setSituacao(vo.getSituacao());
		
		var entityPedido = repository.save(pedido);
		atualizaVo(entityPedido, voReturn);
		List<ProdutoServico> produtoServico = vo.getProdutoServico().stream().map(p -> mapper.map(p, ProdutoServico.class)).collect(Collectors.toList());;
		List<ProdutoServicoVo> lista = new ArrayList<>();
		for(ProdutoServico ps: produtoServico) {
			var produto = produtoRepository.findById(ps.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this product ID"));
			if(produto.getStatus() == Status.ATIVADO) {
				saveItensPedido(pedido, produto, lista);
			}
			
		}
		voReturn.setProdutoServico(lista);
		voReturn.setTotal(calculaTotalPedido(voReturn.getProdutoServico(), voReturn.getDesconto()));
		return voReturn;
	}
	
	protected void saveItensPedido(Pedido pedido, ProdutoServico produto, List<ProdutoServicoVo> lista) {
		ItensPedido itens = new ItensPedido();
		itens.setIdPedido(pedido);
		itens.setIdProdutoServico(produto);
		itensRepository.save(itens);
		lista.add(mapper.map(produto, ProdutoServicoVo.class));
	}
	
	protected void atualizaVo(Pedido pedido, PedidoVO vo) {
		vo.setDesconto(pedido.getDesconto());
		vo.setSituacao(pedido.getSituacao());
		vo.setUuid(pedido.getId());
	}
	
	protected Double calculaTotalPedido(List<ProdutoServicoVo> list, Double desconto) {
		Double totalProduto = 0D;
		Double totalServico = 0D;
		Double total;
		for(ProdutoServicoVo ps : list) {
			if(ps.getTipo() == Tipo.PRODUTO) {
				totalProduto += ps.getValor();
			}else {
				totalServico += ps.getValor();
			}
		}
		if(desconto != null && desconto != 0D) {
			total = totalServico + (totalProduto - ((totalProduto * desconto)/100));
		}else {
			total = totalServico + totalProduto;
		}
		
		return total;
	}

	@Transactional
	public PedidoVO update(PedidoVO vo) {
		Pedido pedido = repository.findById(vo.getUuid()).orElseThrow(() -> new ResourceNotFoundException("No records found for this product ID"));
		verificaCamposPedidoUpdate(pedido, vo);
		repository.save(pedido);
		itensRepository.deleteByIdPedido(pedido);
		PedidoVO voRetorno = new PedidoVO();
		atualizaVo(pedido, voRetorno);
		List<ProdutoServico> produtoServico = vo.getProdutoServico().stream().map(p -> mapper.map(p, ProdutoServico.class)).collect(Collectors.toList());
		List<ProdutoServicoVo> lista = new ArrayList<>();
		for(ProdutoServico ps: produtoServico) {
			var produto = produtoRepository.findById(ps.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this product ID"));
			verificaCamposProdutoUpdate(produto, ps);
			saveItensPedido(pedido, produto, lista);
		}
		
		voRetorno.setProdutoServico(lista);
		voRetorno.setTotal(calculaTotalPedido(voRetorno.getProdutoServico(), voRetorno.getDesconto()));
		return voRetorno;
	}

	private void verificaCamposPedidoUpdate(Pedido pedido, PedidoVO vo) {
		if(vo.getDesconto() != null) {
			pedido.setDesconto(vo.getDesconto());			
		}
		if(vo.getSituacao() != null) {
			pedido.setSituacao(vo.getSituacao());			
		}
		
	}

	private void verificaCamposProdutoUpdate(ProdutoServico ps, ProdutoServico vo) {
		if(vo.getDescricao() != null) {
			ps.setDescricao(vo.getDescricao());
		}
		if(vo.getNome() != null) {
			ps.setNome(vo.getNome());
		}
		if(vo.getStatus() != null) {
			ps.setStatus(vo.getStatus());
		}
		if(vo.getTipo() != null) {
			ps.setTipo(vo.getTipo());
		}
		if(vo.getValor() != null) {
			ps.setValor(vo.getValor());
		}
	}


	
	public PedidoVO findById(String id) {
		UUID uuid = UUID.fromString(id);
		Pedido pedidoEntity = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("No records found for this order ID"));
		var itensEntity = itensRepository.findByIdPedido(pedidoEntity).orElseThrow(() -> new ResourceNotFoundException("No records found for this order"));
		PedidoVO vo = new PedidoVO();
		atualizaVo(pedidoEntity, vo);
		List<ProdutoServicoVo> listaProdutoServico = new ArrayList();
		for(ItensPedido itens: itensEntity){
			ProdutoServico produto = produtoRepository.findById(itens.getIdProdutoServico().getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this order"));
			listaProdutoServico.add(mapper.map(produto, ProdutoServicoVo.class));
		}
		vo.setProdutoServico(listaProdutoServico);
		vo.setTotal(calculaTotalPedido(vo.getProdutoServico(), vo.getDesconto()));
		return vo;
	}

	public List<PedidoVO> findAll() {
		List<PedidoVO> lista = new ArrayList<>();
		var pedidos = repository.findAll();
		for(Pedido pedido: pedidos) {
			PedidoVO po = new PedidoVO();
			List<ProdutoServicoVo> listaProdutos = new ArrayList<>();
			atualizaVo(pedido, po);
			var itens = itensRepository.findByIdPedido(pedido);
			for(ItensPedido item: itens.get()) {
				var produto = produtoRepository.findById(item.getIdProdutoServico().getId());
				listaProdutos.add(mapper.map(produto.get(), ProdutoServicoVo.class));
			}
			po.setProdutoServico(listaProdutos);
			po.setTotal(calculaTotalPedido(listaProdutos, po.getDesconto()));
			lista.add(po);
		}
		
		return lista;
	}

	@Transactional
	public void deleteById(String id) {
		UUID uuid = UUID.fromString(id);
		var pedido = repository.findById(uuid);
		itensRepository.deleteByIdPedido(pedido.get());
		repository.delete(pedido.get());
		
	}

	public Page<PedidoVO> search(Double searchTerm, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<Pedido> pageEntity = repository.search(searchTerm, pageRequest);
		return converterPageEntityToPageVo(pageEntity);
	}
	
	protected Page<PedidoVO> converterPageEntityToPageVo(Page<Pedido> pageEntity ){
		List<Pedido> pedidosEntity = pageEntity.getContent();
		Page<PedidoVO>  po = pageEntity.map(obj -> mapper.map(obj, PedidoVO.class));
		 
		List<PedidoVO> pedidoVo = po.getContent();
		int index = 0;
		for(Pedido pedido: pedidosEntity) {
			List<ProdutoServicoVo> lista = new ArrayList();
			List<ItensPedido> itens = itensRepository.findByIdPedido(pedido).orElseThrow(() -> new ResourceNotFoundException("No records found for this order"));
			for(ItensPedido item : itens) {
				ProdutoServico produto = produtoRepository.findById(item.getIdProdutoServico().getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this order"));
				lista.add(mapper.map(produto, ProdutoServicoVo.class));
			}
			pedidoVo.get(index).setUuid(pedido.getId());
			pedidoVo.get(index).setProdutoServico(lista);
			pedidoVo.get(index).setTotal(calculaTotalPedido(lista, pedido.getDesconto()));
			index++;
		}
		
		
		return po;
	}

	public Page<PedidoVO> pagination(Pageable pageable) {
		Page<Pedido> pageP = repositoryPagination.findAll(pageable);
		return converterPageEntityToPageVo(pageP);
	}
}
