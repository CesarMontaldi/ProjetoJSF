package br.com.cesarmontaldi.repository;

import br.com.cesarmontaldi.model.Pessoa;

public interface DaoPessoa {
	
	Pessoa consultarUser(String login, String senha);

}
