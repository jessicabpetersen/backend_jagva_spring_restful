package br.com.jessicabpetersen.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_pedido")
public class ItensPedido  implements Serializable, Persistable<UUID> {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@OneToOne
	@JoinColumn(name = "id_pedido")
	private Pedido idPedido;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "id_produto_servico", referencedColumnName = "id", nullable = true)
	@NotNull
	private ProdutoServico idProdutoServico;

	public ItensPedido() {}
	
	public ItensPedido(UUID id, Pedido idPedido, ProdutoServico idProdutoServico) {
		super();
		this.id = id;
		this.idPedido = idPedido;
		this.idProdutoServico = idProdutoServico;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Pedido getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Pedido idPedido) {
		this.idPedido = idPedido;
	}

	public ProdutoServico getIdProdutoServico() {
		return idProdutoServico;
	}

	public void setIdProdutoServico(ProdutoServico idProdutoServico) {
		this.idProdutoServico = idProdutoServico;
	}

	@Override
	public boolean isNew() {
		  return id == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idPedido, idProdutoServico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItensPedido other = (ItensPedido) obj;
		return Objects.equals(id, other.id) && Objects.equals(idPedido, other.idPedido)
				&& Objects.equals(idProdutoServico, other.idProdutoServico);
	}
}
