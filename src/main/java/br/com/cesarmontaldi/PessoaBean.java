package br.com.cesarmontaldi;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoPessoa;
import br.com.cesarmontaldi.repository.DaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	private DaoPessoa daoPessoa = new DaoPessoaImpl();
		
		
	
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
	
	public String login() {
		
		Pessoa user = daoPessoa.consultarUser(pessoa.getLogin(), pessoa.getSenha());
		
		if (user != null) { // Encontrou o usário com o login e senha informado
			
			// adicionar o usuário na sessão
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("userLogado", user);
			
			return "primeiraPagina.jsf";
		}
		
		return "index.jsf";
	}
	
	public boolean allowAccess(String acesso) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa user = (Pessoa) externalContext.getSessionMap().get("userLogado");
		 
		return user.getPerfil().equals(acesso);
	}
	
}
