package br.com.cesarmontaldi.repository;

import java.util.List;

import br.com.cesarmontaldi.model.Lancamento;

public interface DaoLancamento {
	
	List<Lancamento> consultar(Long idUser);

}
