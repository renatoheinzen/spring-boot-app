package com.unisul.domain.enumns;

public enum TipoEstadoPagamento {
	
	PENDENTE (1, "Pendente"),
	QUITADO (2,"Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int codigo;
	private String descricao;
	
	private TipoEstadoPagamento (int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoEstadoPagamento toEnum (Integer codigo) {
		
		if (codigo == null) {
			return null;
		}
		
		for(TipoEstadoPagamento x: TipoEstadoPagamento.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido para Tipo de cliente: "+codigo);
		
	}
	

}
