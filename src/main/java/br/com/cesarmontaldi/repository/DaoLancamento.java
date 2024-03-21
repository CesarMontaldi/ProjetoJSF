package br.com.cesarmontaldi.repository;

import java.util.Date;
import java.util.List;

import br.com.cesarmontaldi.model.Lancamento;

public interface DaoLancamento {
	
	List<Lancamento> consultar(Long idUser);
	
	List<Lancamento> consultaPaginada(Long idUser);
	
	List<Lancamento> relatorioLancamento(String numeroNota, Date dataInicial, Date dataFim); 
	
}
