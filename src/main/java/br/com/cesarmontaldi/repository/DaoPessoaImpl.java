package br.com.cesarmontaldi.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.cesarmontaldi.model.Estados;
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
		
		user = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult(); 
		
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
	
	

}
