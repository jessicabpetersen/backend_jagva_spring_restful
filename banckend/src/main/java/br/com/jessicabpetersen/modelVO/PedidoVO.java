package br.com.jessicabpetersen.modelVO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PedidoVO {
	
	private UUID id;

	private Double desconto;
	
	private Situacao situacao;
	
	private Double total;
	
	private List<ProdutoServicoVo> produtoServico;
	
	public PedidoVO() {}


	public PedidoVO(UUID uuid, Double desconto, Situacao situacao, Double total,
			List<ProdutoServicoVo> produtoServico) {
		super();
		this.id = uuid;
		this.desconto = desconto;
		this.situacao = situacao;
		this.total = total;
		this.produtoServico = produtoServico;
	}


	@Override
	public int hashCode() {
		return Objects.hash(desconto, produtoServico, situacao, total, id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoVO other = (PedidoVO) obj;
		return Objects.equals(desconto, other.desconto) && Objects.equals(produtoServico, other.produtoServico)
				&& situacao == other.situacao && Objects.equals(total, other.total) && Objects.equals(id, other.id);
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID uuid) {
		this.id = uuid;
	}


	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<ProdutoServicoVo> getProdutoServico() {
		return produtoServico;
	}

	public void setProdutoServico(List<ProdutoServicoVo> produtoServico) {
		this.produtoServico = produtoServico;
	}
	
	
}
