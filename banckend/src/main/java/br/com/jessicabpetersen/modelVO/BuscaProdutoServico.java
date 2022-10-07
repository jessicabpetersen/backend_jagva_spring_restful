package br.com.jessicabpetersen.modelVO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.com.jessicabpetersen.model.ProdutoServico;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public class BuscaProdutoServico {

	private String nome;
	
	private String descricao;
	
	private Double valor;
	
	private Tipo tipo;
	
	private Status status;
	
	public BuscaProdutoServico() {}

	public BuscaProdutoServico(String nome, String descricao, Double valor, Tipo tipo, Status status) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.valor = valor;
		this.tipo = tipo;
		this.status = status;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getValor() {
		return valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Status getStatus() {
		return status;
	}
	
	public Specification<ProdutoServico> toSpec(){
		return (root, query, builder) -> {
			List<Predicate> predicados = new ArrayList<>();
			if(StringUtils.hasText(nome)) {
				Path<String> campoNome = root.<String>get("nome");
				Predicate predicatoNome = builder.equal(campoNome, nome);
				predicados.add(predicatoNome);
			}
			return builder.and(predicados.toArray(new Predicate[0]));
		};
	}
	
}
