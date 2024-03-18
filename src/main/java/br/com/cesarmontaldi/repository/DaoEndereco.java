package br.com.cesarmontaldi.repository;

import java.util.List;

import br.com.cesarmontaldi.model.Endereco;

public interface DaoEndereco {
	
	Endereco salvarEndereco(Endereco endereco);
	
	List<Endereco> consultarEnderecos();
	
	Endereco consultaEndereco(Long idUser);
	
}
