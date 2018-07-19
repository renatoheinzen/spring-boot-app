package com.unisul.tcc.domain;

import javax.persistence.Entity;

import com.unisul.tcc.domain.enumns.TipoEstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	private Integer numeroParcelas;
	
	public PagamentoComCartao() {}

	public PagamentoComCartao(Integer id, TipoEstadoPagamento tipo, Pedido pedido, Integer numeroParcelas) {
		super(id, tipo, pedido);
		this.numeroParcelas = numeroParcelas;
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	
	
	
	

}
