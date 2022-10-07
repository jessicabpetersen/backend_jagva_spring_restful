package br.com.jessicabpetersen.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.http.HttpStatus;

import br.com.jessicabpetersen.modelVO.BuscaProdutoServico;
import br.com.jessicabpetersen.modelVO.ProdutoServicoVo;
import br.com.jessicabpetersen.service.ProdutoServicoService;

@RestController
@RequestMapping(path = "/v1/produto-servico")
public class ProdutoServicoController {
	
	@Autowired
	protected ProdutoServicoService service;
		
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoServicoVo save(@RequestBody @Validated ProdutoServicoVo vo) {
		return service.save(vo);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ProdutoServicoVo update(@RequestBody @Validated ProdutoServicoVo vo) {
		return service.update(vo);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoServicoVo findById(@PathVariable("id") final String id) {
		return service.findById(id);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProdutoServicoVo> findAll() {
		return service.findAll();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteById(@PathVariable("id") final String id) {
		service.deleteById(id);
	}
	
	@GetMapping("/search")
	public Page<ProdutoServicoVo> search(@RequestParam("searchTerm") String searchTerm,
								@RequestParam(value = "page", required=false, defaultValue = "0") int page,
								@RequestParam(value = "size", required=false, defaultValue = "10") int size){
		return service.search(searchTerm, page, size);
	}

	
	@GetMapping("/pagination")
	public Page<ProdutoServicoVo> getPagination(@PageableDefault(direction = Direction.ASC, sort = "id") Pageable pageable){
		return service.getPatination(pageable);
	}
	
	@GetMapping("/pagination-filter")
	public Page<ProdutoServicoVo> getPaginationFilter(@PageableDefault(direction = Direction.ASC, sort = "id") Pageable pageable, BuscaProdutoServico busca){
		return service.getPaginationFilter(pageable, busca);
	}

}
