package br.com.jessicabpetersen.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.jessicabpetersen.modelVO.ProdutoServicoVo;
import br.com.jessicabpetersen.modelVO.Status;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class ProdutoServicoServiceTest {
	
	@Autowired
	private ProdutoServicoService service;
	
	@Test
	public void buscaPorIdTeste() {
		ProdutoServicoVo produtoServico = service.findById("4cd9b09e-b7c7-442d-b952-a8c6a9895897");
		assertEquals(UUID.fromString("4cd9b09e-b7c7-442d-b952-a8c6a9895897"), produtoServico.getId());
	}
	
	@Test
	public void saveTeste() {
		ProdutoServicoVo produto = new ProdutoServicoVo();
		produto.setDescricao("descricao");
		produto.setNome("nome");
		produto.setStatus(Status.ATIVADO);
		produto.setValor(200D);
		ProdutoServicoVo saved = service.save(produto);
		
		assertEquals(produto.getNome(), saved.getNome());
		assertEquals(produto.getDescricao(), saved.getDescricao());
		assertNotNull(saved);
	}
	
	@Test
	public void updateTeste() {
		ProdutoServicoVo produto = new ProdutoServicoVo();
		produto.setId(UUID.fromString("4cd9b09e-b7c7-442d-b952-a8c6a9895897"));
		produto.setValor(200D);
		ProdutoServicoVo saved = service.update(produto);
		
		assertEquals(produto.getId(), saved.getId());
		assertEquals(produto.getValor(), produto.getValor());
		assertEquals("teclado", saved.getNome());
	}

}
