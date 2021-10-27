package com.laerson.treino.algaworks.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laerson.treino.algaworks.springmvc.model.Ordem;

public interface OrdemRepository extends JpaRepository<Ordem, Long>{
	
	public List<Ordem> findByClienteContaining(String descricao);

}
