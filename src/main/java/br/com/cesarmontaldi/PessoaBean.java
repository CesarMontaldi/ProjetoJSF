package br.com.cesarmontaldi;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String salvar() {
		pessoa = daoGeneric.salvarUser(pessoa);
		return "";
	}
	
	public void novo() {
		pessoa = new Pessoa();
	}
	
	public void delete() {
		daoGeneric.deleteUserId(pessoa);
		pessoa = new Pessoa();
	}
	
}
