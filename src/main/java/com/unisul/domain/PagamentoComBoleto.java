package com.unisul.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.unisul.domain.enumns.TipoEstadoPagamento;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern= "dd/MM/yyy")
	private Date dataPagamento;
	
	@JsonFormat(pattern= "dd/MM/yyy")
	private Date dataVencimento;

	public PagamentoComBoleto() {}

	public PagamentoComBoleto(Integer id, TipoEstadoPagamento tipo, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, tipo, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	
	
	
}
