package br.com.cesarmontaldi.repository;

import java.util.List;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoEnderecoImpl implements DaoEndereco {

	@Override
	public List<Endereco> consultarEnderecos() {
		
		List<Endereco> enderecos = null;
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		enderecos = entityManager.createQuery(" from Endereco ").getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return enderecos;
	}
	
	public Endereco consultaEndereco(Long idUser) {
		
		Endereco endereco = null;
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		endereco = (Endereco) entityManager.createQuery(" from Endereco where usuario.id= " + idUser).getSingleResult();
		
		transaction.commit();
		entityManager.close();
		
		return endereco;
	}

}
