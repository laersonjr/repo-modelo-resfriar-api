package com.laerson.treino.algaworks.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laerson.treino.algaworks.springmvc.model.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Long>{
	
	public List<Titulo> findByClienteContaining(String descricao);

}
