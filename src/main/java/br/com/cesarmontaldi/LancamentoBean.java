package br.com.cesarmontaldi;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Lancamento;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoLancamento;
import br.com.cesarmontaldi.repository.DaoLancamentoImpl;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {
	
	private Lancamento lancamento = new Lancamento();
	private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private DaoLancamento daoLancamento = new DaoLancamentoImpl(); 
	private Pessoa pessoa = new Pessoa();
	
	
	public void novo() {
		lancamento = new Lancamento();
	}
	
	
	public void salvar() {
		
		Pessoa user = pessoa.getUserLogado();
		lancamento.setUsuario(user);
		lancamento = daoGeneric.salvarEntity(lancamento);

		carregarLancamentos();
	}
	
	
	@PostConstruct
	private void carregarLancamentos() {
		
		Pessoa user = pessoa.getUserLogado();
		
		if (user.getPerfil().equalsIgnoreCase("ADMINISTRADOR")) {
			lancamentos = daoGeneric.getListEntity(Lancamento.class);
		} 
		else {
			lancamentos = daoLancamento.consultar(user.getId());
		}
	}
	
	public String delete() {
		daoGeneric.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentos();
		
		return "";
	}

	
	public Lancamento getLancamento() {
		return lancamento;
	}
	
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}
	
	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}


	public DaoLancamento getDaoLancamento() {
		return daoLancamento;
	}


	public void setDaoLancamento(DaoLancamento daoLancamento) {
		this.daoLancamento = daoLancamento;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
