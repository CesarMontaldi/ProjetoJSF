package br.com.cesarmontaldi.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.cesarmontaldi.model.Pessoa;

public interface DaoPessoa {
	
	Pessoa consultarUser(String login, String senha);
	
	List<SelectItem> listaEstados();
}
