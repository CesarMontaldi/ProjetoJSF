package br.com.cesarmontaldi.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.cesarmontaldi.model.Lancamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoLancamentoImpl implements DaoLancamento, Serializable {

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
	
	@Override
	public List<Lancamento> consultaPaginada(Long idUser) {
		
		List<Lancamento> lancamentos = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager
				.createQuery(" from Lancamento where usuario.id= " + idUser + " order by id ASC ")
				.setMaxResults(10)
				.getResultList();
		
		transaction.commit();
		
		return lancamentos;
	}

	@Override
	public List<Lancamento> relatorioLancamento(String numeroNota, Date dataInicial, Date dataFim) {
		
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select l from Lancamento l ");
		
		if (dataInicial == null && dataFim == null && numeroNota != null && !numeroNota.isEmpty()) {
			sql.append(" where l.numeroNotaFiscal = '").append(numeroNota.trim()).append("'");
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager.createQuery(sql.toString()).getResultList();
		
		transaction.commit();
		
		return lancamentos;
	}

}
