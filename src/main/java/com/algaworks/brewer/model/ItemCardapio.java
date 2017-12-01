package com.algaworks.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="item_cardapio")
public class ItemCardapio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	
	
	@NotBlank(message = "O nome da cidade é obrigatório")
	@Size(max = 20, message = "O tamanho do nome deve estar entre 1 e 20")
	private String nome;
	
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.10", message = "O valor da cerveja deve ser maior que R$0,10")
	@DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$9.999.999,99")
	private BigDecimal valor;
	
	private String foto;
	
	
	@Column(name="content_type")
	private String contentType;
	
	@NotBlank(message = "A descrição é obrigatória")
	@Size(max = 500, message = "O tamanho da descrição deve estar entre 1 e 500")
	private String descricao;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	
	@ManyToOne
	@JoinColumn(name = "codigo_pedido_online")
	private PedidoOnline pedidoOnline;
	
	
	@PrePersist @PreUpdate
	private void prePersistPreUpdate() {
		
		
		
		String tenantID = TenancyInterceptor.getTenantId();
		
		if(tenantID != null) {
			
			this.tenantId = tenantID;
		}
	}


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	


	public PedidoOnline getPedidoOnline() {
		return pedidoOnline;
	}


	public void setPedidoOnline(PedidoOnline pedidoOnline) {
		this.pedidoOnline = pedidoOnline;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCardapio other = (ItemCardapio) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	

}
