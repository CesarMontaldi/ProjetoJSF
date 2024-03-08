package br.com.cesarmontaldi.repository;

import java.util.List;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoEnderecoImpl implements DaoEndereco {

	@Override
	public List<Endereco> consultarEndereco(Long idUser) {
		
		List<Endereco> enderecos = null;
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		enderecos = entityManager.createQuery(" select cep from Endereco where usuario.id= " + idUser).getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return enderecos;
	}

}
