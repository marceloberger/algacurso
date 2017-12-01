package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="pedido_online")
public class PedidoOnline implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@OneToMany(mappedBy = "pedidoOnline")
	private List<HorarioFuncionamento> horarios;
	
	@Column(name = "endereco_restaurante")
	private String enderecoRestaurante;
	
	private String latitude;
	
	private String longitude;
	
	@Column(name = "raio_entrega")
	private String raioEntrega;
	
	@Column(name = "valor_minimo")
	private String valorMinimo;
	
	@Column(name = "valor_entrega")
	private String taxaEntrega;
	
	@Column(name = "valor_gratuito")
	private String valorGratuitoEntrega;
	
	@Column(name = "tempo_entrega")
	private String tempoEntrega;
	
	@Column(name = "taxa_conveniencia")
	private String taxaConveniencia;
	
	private String moeda;
	
	private String formaPagamento;
	
	private String imposto;
	
	
	@OneToMany(mappedBy = "pedidoOnline")
	private List<ItemCardapio> itens;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	
	
	@PrePersist @PreUpdate
	private void definirTenant() {
		
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



	public List<HorarioFuncionamento> getHorarios() {
		return horarios;
	}



	public void setHorarios(List<HorarioFuncionamento> horarios) {
		this.horarios = horarios;
	}



	public String getEnderecoRestaurante() {
		return enderecoRestaurante;
	}



	public void setEnderecoRestaurante(String enderecoRestaurante) {
		this.enderecoRestaurante = enderecoRestaurante;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getRaioEntrega() {
		return raioEntrega;
	}



	public void setRaioEntrega(String raioEntrega) {
		this.raioEntrega = raioEntrega;
	}



	public String getValorMinimo() {
		return valorMinimo;
	}



	public void setValorMinimo(String valorMinimo) {
		this.valorMinimo = valorMinimo;
	}



	public String getTaxaEntrega() {
		return taxaEntrega;
	}



	public void setTaxaEntrega(String taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}



	



	public String getValorGratuitoEntrega() {
		return valorGratuitoEntrega;
	}



	public void setValorGratuitoEntrega(String valorGratuitoEntrega) {
		this.valorGratuitoEntrega = valorGratuitoEntrega;
	}



	public String getTempoEntrega() {
		return tempoEntrega;
	}



	public void setTempoEntrega(String tempoEntrega) {
		this.tempoEntrega = tempoEntrega;
	}



	public String getTaxaConveniencia() {
		return taxaConveniencia;
	}



	public void setTaxaConveniencia(String taxaConveniencia) {
		this.taxaConveniencia = taxaConveniencia;
	}



	public String getMoeda() {
		return moeda;
	}



	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}



	public String getFormaPagamento() {
		return formaPagamento;
	}



	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}



	public String getImposto() {
		return imposto;
	}



	public void setImposto(String imposto) {
		this.imposto = imposto;
	}



	public List<ItemCardapio> getItens() {
		return itens;
	}



	public void setItens(List<ItemCardapio> itens) {
		this.itens = itens;
	}



	public String getTenantId() {
		return tenantId;
	}



	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
		PedidoOnline other = (PedidoOnline) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
