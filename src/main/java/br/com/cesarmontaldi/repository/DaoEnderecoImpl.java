package br.com.cesarmontaldi.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.cesarmontaldi.model.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoEnderecoImpl implements DaoEndereco, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;

	@Override
	public List<Endereco> consultarEnderecos() {
		List<Endereco> enderecos = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		enderecos = entityManager.createQuery(" from Endereco ").getResultList();
		
		transaction.commit();
		
		return enderecos;
	}
	
	public Endereco consultaEndereco(Long idUser) {
		
		Endereco endereco = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		endereco = (Endereco) entityManager.createQuery(" from Endereco where usuario.id= " + idUser).getSingleResult();
		
		transaction.commit();
		
		return endereco;
	}

}
