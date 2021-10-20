package com.laerson.treino.algaworks.springmvc.model;

public enum StatusTitulo {
	
	PENDENTE("Pendente"),
	CONCLUÍDO("Concluído");
	
	private String descricao;
	
	StatusTitulo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
