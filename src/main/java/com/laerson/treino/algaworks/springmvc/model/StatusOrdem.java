package com.laerson.treino.algaworks.springmvc.model;

public enum StatusOrdem {
	
	PENDENTE("Pendente"),
	CONCLUÍDO("Concluído");
	
	private String descricao;
	
	StatusOrdem(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
