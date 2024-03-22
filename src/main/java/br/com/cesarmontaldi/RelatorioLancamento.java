package br.com.cesarmontaldi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Lancamento;
import br.com.cesarmontaldi.repository.DaoLancamento;

@ViewScoped
@Named(value = "relLancamentoBean")
public class RelatorioLancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String numeroNotaFiscal;

	@Inject
	private DaoLancamento daoLancamento;
	
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	
	public void novo() {
		dataInicio = null;
		dataFim = null;
		numeroNotaFiscal = null;
		lancamentos = new ArrayList<Lancamento>();
	}
	
	public void buscarLancamento() {
		
		if (dataInicio == null && dataFim == null && (numeroNotaFiscal == null || numeroNotaFiscal.isEmpty())) {
			lancamentos = daoGeneric.getListEntity(Lancamento.class);
		} 
		else {
			lancamentos = daoLancamento.relatorioLancamento(numeroNotaFiscal, dataInicio, dataFim);
		}
		
		
	}
}
