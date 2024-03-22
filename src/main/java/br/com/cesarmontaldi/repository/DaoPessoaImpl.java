package br.com.cesarmontaldi.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.cesarmontaldi.model.Estados;
import br.com.cesarmontaldi.model.Lancamento;
import br.com.cesarmontaldi.model.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoPessoaImpl implements DaoPessoa, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;

	@Override
	public Pessoa consultarUser(String login, String senha) {
		
		Pessoa user = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {
		user = (Pessoa) entityManager
				.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'")
				.getSingleResult(); 
		} 
		catch (jakarta.persistence.NoResultException e) { /* Tratamento senão encontrar usuário com o login e senha*/
			e.printStackTrace();
		}
		
		transaction.commit();
		
		return user;
	}

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<Estados> estados = entityManager.createQuery("from Estados").getResultList();
		
		for (Estados estado : estados) {
			
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		
		transaction.commit();
		
		return selectItems;
	}
	
	@Override
	public List<Pessoa> consultaPaginada() {
		
		List<Pessoa> pessoas = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		pessoas = entityManager
				.createQuery(" from Pessoa order by id ASC ")
				.setMaxResults(10)
				.getResultList();
		
		transaction.commit();
		
		return pessoas;
	}
	
	@Override
	public List<Pessoa> relatorioPessoas(String nome, Date dataInicial, Date dataFim) {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select * from Pessoa ");
		
		if (dataInicial == null && dataFim == null && nome != null && !nome.isEmpty()) {
			sql.append("where upper(nome) like '%").append(nome.trim().toUpperCase()).append("%'");
		}
		
		else if (nome == null || (nome != null && nome.isEmpty()) && dataInicial != null && dataFim == null) {
			
			String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			sql.append("where dataNascimento >= '").append(dataInicio).append("'");
		}
		
		else if (nome == null || (nome != null && nome.isEmpty()) && dataInicial == null && dataFim != null) {
			
			String dataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append("where dataNascimento <= '").append(dataFinal).append("'");
		}
		
		else if (nome == null || (nome != null && nome.isEmpty()) && dataInicial != null && dataFim != null){
			
			String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append("where dataNascimento >= '").append(dataInicio).append("' ");
			sql.append("and dataNascimento <= '").append(dataFinal).append("' ");
		}
		
		else if (nome != null && !nome.isEmpty() && dataInicial != null && dataFim != null){
			
			String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append("where dataNascimento >= '").append(dataInicio).append("' ");
			sql.append("and dataNascimento <= '").append(dataFinal).append("' ");
			sql.append("and upper(nome) like '%").append(nome.trim().toUpperCase()).append("%'");
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		pessoas = (List<Pessoa>) entityManager.createNativeQuery(sql.toString(), Pessoa.class).getResultList();
		
		transaction.commit();
		
		return pessoas;
	}

}
