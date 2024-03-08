package br.com.cesarmontaldi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoEndereco;
import br.com.cesarmontaldi.repository.DaoPessoa;
import br.com.cesarmontaldi.repository.DaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private DaoPessoa daoPessoa = new DaoPessoaImpl();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private EnderecoBean enderecoBean = new EnderecoBean();
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String salvar() {
		pessoa = daoGeneric.salvarEntity(pessoa);
		carregarPessoas();
		getMsg("Cadastrado com sucesso!");
		return "";
	}
	
	public void novo() {
		pessoa = new Pessoa();
		getMsg("");
	}
	
	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	public Pessoa editar() {
		Pessoa user = daoGeneric.buscar(pessoa);
		
		return user;
	}
	
	public void delete() {
		daoGeneric.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		getMsg("Removido com sucesso!");
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
			
			return "principal.jsf";
		}
		
		return "index.jsf";
	}
	
	public boolean allowAccess(String acesso) {
		Pessoa user = pessoa.getUserLogado();
		return user.getPerfil().equals(acesso);
	}
	
	public void getMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
	
}
