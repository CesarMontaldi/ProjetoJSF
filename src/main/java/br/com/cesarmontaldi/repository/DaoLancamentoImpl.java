package br.com.cesarmontaldi.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.cesarmontaldi.model.Lancamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoLancamentoImpl implements DaoLancamento, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	

	@Override
	public List<Lancamento> consultar(Long idUser) {
		
		List<Lancamento> lancamentos = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager.createQuery(" from Lancamento where usuario.id= " + idUser + " order by id ASC ").getResultList();
		
		transaction.commit();
		
		return lancamentos;
	}

}
