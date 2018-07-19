package com.unisul.tcc.resources.exceptions;

import java.io.Serializable;

public class StandError implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer error;
	private String mensagem;
	private Long timeStamp;
	
	public StandError(Integer error, String mensagem, Long timeStamp) {
		super();
		this.error = error;
		this.mensagem = mensagem;
		this.timeStamp = timeStamp;
	}
	
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
