package br.com.cesarmontaldi;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String salvar() {
		pessoa = daoGeneric.salvarUser(pessoa);
		CarregarPessoas();
		return "";
	}
	
	public void novo() {
		pessoa = new Pessoa();
	}
	
	@PostConstruct
	public void CarregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	public void delete() {
		daoGeneric.deleteUserId(pessoa);
		CarregarPessoas();
		pessoa = new Pessoa();
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
}
