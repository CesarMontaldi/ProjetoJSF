package br.com.cesarmontaldi.repository;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.cesarmontaldi.model.Lancamento;
import br.com.cesarmontaldi.model.Pessoa;

public interface DaoPessoa {
	
	Pessoa consultarUser(String login, String senha);
	
	List<SelectItem> listaEstados();
	
	List<Pessoa> consultaPaginada();
	
	List<Pessoa> relatorioPessoas(String nome, Date dataInicio, Date dataFim);
}
