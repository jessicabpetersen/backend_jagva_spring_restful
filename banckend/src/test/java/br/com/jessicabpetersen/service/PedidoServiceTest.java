package br.com.jessicabpetersen.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.jessicabpetersen.modelVO.PedidoVO;
import br.com.jessicabpetersen.modelVO.ProdutoServicoVo;
import br.com.jessicabpetersen.modelVO.Situacao;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PedidoServiceTest {
	
	@Autowired
	private PedidoService service;
	
	@Test
	public void saveTest() {
		PedidoVO vo = new PedidoVO();
		vo.setDesconto(10D);
		vo.setSituacao(Situacao.ABERTO);
		List<ProdutoServicoVo> lista = new ArrayList<>();
		ProdutoServicoVo produto = new ProdutoServicoVo();
		produto.setId(UUID.fromString("3ea56436-cf56-46e7-b9e6-27a1db71a536"));
		lista.add(produto);
		vo.setProdutoServico(lista);
		
		PedidoVO saved = service.save(vo);
		
		assertEquals(vo.getDesconto(), saved.getDesconto());
		assertEquals(vo.getSituacao(), saved.getSituacao());
		assertNotNull(saved);
	}
	
	@Test
	public void updateTest() {
		PedidoVO vo = new PedidoVO();
		vo.setDesconto(20D);
		vo.setId(UUID.fromString("07c37d95-8e28-4524-860c-4f8bc8293fb6"));
		List<ProdutoServicoVo> lista = new ArrayList<>();
		ProdutoServicoVo produto = new ProdutoServicoVo();
		produto.setId(UUID.fromString("4cd9b09e-b7c7-442d-b952-a8c6a9895897"));
		lista.add(produto);
		ProdutoServicoVo produto2 = new ProdutoServicoVo();
		produto2.setId(UUID.fromString("3ea56436-cf56-46e7-b9e6-27a1db71a536"));
		lista.add(produto2);
		vo.setProdutoServico(lista);
		
		PedidoVO updated = service.update(vo);
		
		assertNotNull(updated);
		assertEquals(vo.getDesconto(), updated.getDesconto());
		assertEquals(vo.getId(), updated.getId());
	}
	
	@Test
	public void getByIdTest() {
		PedidoVO vo = service.findById("073be6c4-2a7e-46d9-aa6e-2732aab16769");
		assertNotNull(vo);
		assertEquals(Double.valueOf(10),vo.getDesconto());
		
	}

}
