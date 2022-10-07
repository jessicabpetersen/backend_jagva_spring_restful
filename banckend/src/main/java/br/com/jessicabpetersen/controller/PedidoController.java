package br.com.jessicabpetersen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jessicabpetersen.modelVO.PedidoVO;
import br.com.jessicabpetersen.modelVO.ProdutoServicoVo;
import br.com.jessicabpetersen.service.PedidoService;
import br.com.jessicabpetersen.service.ProdutoServicoService;

@RestController
@RequestMapping(path = "/v1/pedido")
public class PedidoController {

	@Autowired
	protected PedidoService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoVO save(@RequestBody @Validated PedidoVO vo) {
		return service.save(vo);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public PedidoVO update(@RequestBody @Validated PedidoVO vo) {
		return service.update(vo);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PedidoVO findById(@PathVariable("id") final String id) {
		return service.findById(id);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PedidoVO> findAll() {
		return service.findAll();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteById(@PathVariable("id") final String id) {
		service.deleteById(id);
	}
	
	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public Page<PedidoVO> search(@RequestParam("searchTerm") Double searchTerm,
							@RequestParam(value = "page", required=false, defaultValue = "0") int page,
							@RequestParam(value = "size", required=false, defaultValue = "10") int size){
		return service.search(searchTerm, page, size);
	}
	
	@GetMapping("/pagination")
	@ResponseStatus(HttpStatus.OK)
	public Page<PedidoVO> pagination(@PageableDefault(direction = Direction.ASC, sort = "id") Pageable pageable){
		return service.pagination(pageable);
	}
	
}
