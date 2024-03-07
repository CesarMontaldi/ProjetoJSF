package br.com.cesarmontaldi.repository;

import java.util.List;

import br.com.cesarmontaldi.model.Endereco;

public interface DaoEndereco {
	
	List<Endereco> consultarEndereco(Long idUser);
}
