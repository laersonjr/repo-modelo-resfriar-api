package com.laerson.treino.algaworks.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.laerson.treino.algaworks.springmvc.model.StatusTitulo;
import com.laerson.treino.algaworks.springmvc.model.Titulo;
import com.laerson.treino.algaworks.springmvc.repository.TituloRepository;
import com.laerson.treino.algaworks.springmvc.repository.filter.TituloFilter;


@Service
public class CadastroTituloService {

	@Autowired
	private TituloRepository tituloRepository;

	public void salvar(Titulo titulo) {
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
		Titulo titulo = tituloRepository.getOne(codigo);
		titulo.setStatus(StatusTitulo.CONCLUÍDO);
		tituloRepository.save(titulo);
		
		return StatusTitulo.CONCLUÍDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String cliente = filtro.getCliente() == null ? "" : filtro.getCliente();
		return tituloRepository.findByClienteContaining(cliente);
	}

}
