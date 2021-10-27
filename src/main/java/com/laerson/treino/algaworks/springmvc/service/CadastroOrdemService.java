package com.laerson.treino.algaworks.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.laerson.treino.algaworks.springmvc.model.StatusOrdem;
import com.laerson.treino.algaworks.springmvc.model.Ordem;
import com.laerson.treino.algaworks.springmvc.repository.OrdemRepository;
import com.laerson.treino.algaworks.springmvc.repository.filter.OrdemFilter;


@Service
public class CadastroOrdemService {

	@Autowired
	private OrdemRepository tituloRepository;

	public void salvar(Ordem titulo) {
		try {
			tituloRepository.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public void excluir(Long codigo) {
		tituloRepository.deleteById(codigo);
	}

	public String receber(Long codigo) {
		Ordem titulo = tituloRepository.getOne(codigo);
		titulo.setStatus(StatusOrdem.CONCLUÍDO);
		tituloRepository.save(titulo);
		
		return StatusOrdem.CONCLUÍDO.getDescricao();
	}
	
	public List<Ordem> filtrar(OrdemFilter filtro){
		String cliente = filtro.getCliente() == null ? "" : filtro.getCliente();
		return tituloRepository.findByClienteContaining(cliente);
	}

}
