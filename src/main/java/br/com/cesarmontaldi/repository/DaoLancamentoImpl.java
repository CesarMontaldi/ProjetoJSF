package br.com.cesarmontaldi.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
		
		sql.append("select * from Lancamento ");
		
		if (dataInicial == null && dataFim == null && numeroNota != null && !numeroNota.isEmpty()) {
			sql.append("where numeroNotaFiscal = '").append(numeroNota.trim()).append("'");
		}
		
		else if (numeroNota == null || (numeroNota != null && numeroNota.isEmpty()) && dataInicial != null && dataFim == null) {
			
			String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			sql.append("where dataCadastro >= '").append(dataInicio).append("'");
		}
		
		else if (numeroNota == null || (numeroNota != null && numeroNota.isEmpty()) && dataInicial == null && dataFim != null) {
			
			String dataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append("where dataCadastro <= '").append(dataFinal).append("'");
		}
		
		else if (numeroNota == null || (numeroNota != null && numeroNota.isEmpty()) && dataInicial != null && dataFim != null){
			
			String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append("where dataCadastro >= '").append(dataInicio).append("' ");
			sql.append("and dataCadastro <= '").append(dataFinal).append("' ");
		}
		
		else if (numeroNota != null && !numeroNota.isEmpty() && dataInicial != null && dataFim != null){
			
			String dataInicio = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);
			String dataFinal = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append("where dataCadastro >= '").append(dataInicio).append("' ");
			sql.append("and dataCadastro <= '").append(dataFinal).append("' ");
			sql.append("and numeroNotaFiscal = '").append(numeroNota.trim()).append("'");
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = (List<Lancamento>) entityManager.createNativeQuery(sql.toString(), Lancamento.class).getResultList();
		
		transaction.commit();
		
		return lancamentos;
	}

}
