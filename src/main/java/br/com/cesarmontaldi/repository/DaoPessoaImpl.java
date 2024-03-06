package br.com.cesarmontaldi.repository;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoPessoaImpl implements DaoPessoa{

	@Override
	public Pessoa consultarUser(String login, String senha) {
		
		Pessoa user = null;
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		user = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult(); 
		
		transaction.commit();
		
		return user;
	}
	
	

}
