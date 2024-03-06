package br.com.cesarmontaldi.repository;

import java.util.List;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Lancamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoLancamentoImpl implements DaoLancamento {

	@Override
	public List<Lancamento> consultar(Long idUser) {
		
		List<Lancamento> lancamentos = null;
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager.createQuery(" from Lancamento where usuario.id= " + idUser + " order by id ASC ").getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return lancamentos;
	}

}
