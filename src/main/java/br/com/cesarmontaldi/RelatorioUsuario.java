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
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoPessoa;

@ViewScoped
@Named(value = "relPessoaBean")
public class RelatorioUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@Inject
	private DaoPessoa daoPessoa;
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String nome;

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public DaoPessoa getDaoPessoa() {
		return daoPessoa;
	}

	public void setDaoPessoa(DaoPessoa daoPessoa) {
		this.daoPessoa = daoPessoa;
	}
	
	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public void relatorioPessoas() {
		
		if (dataInicio == null && dataFim == null && (nome == null || nome.isEmpty())) {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
		} 
		else {
			pessoas = daoPessoa.relatorioPessoas(nome, dataInicio, dataFim);
		}
	}

	
}
