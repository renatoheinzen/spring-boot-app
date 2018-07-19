package com.unisul.tcc.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.unisul.tcc.domain.enumns.TipoEstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;
	
	@Temporal(TemporalType.DATE)
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
