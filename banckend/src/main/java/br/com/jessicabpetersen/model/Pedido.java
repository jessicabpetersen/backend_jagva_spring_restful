package br.com.jessicabpetersen.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import br.com.jessicabpetersen.modelVO.Situacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable, Persistable<UUID> {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	private Double desconto;
	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Situacao situacao;
	
	public Pedido() {}

	public Pedido(UUID id, Double desconto, Situacao situacao) {
		super();
		this.id = id;
		this.desconto = desconto;
		this.situacao = situacao;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
	
	@Override
	public boolean isNew() {
		  return id == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(desconto, id, situacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(desconto, other.desconto) && Objects.equals(id, other.id) && situacao == other.situacao;
	}

}
