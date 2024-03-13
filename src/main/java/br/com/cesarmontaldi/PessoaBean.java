package br.com.cesarmontaldi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Endereco;
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
	private Endereco endereco = new Endereco();
	private EnderecoBean enderecoBean = new EnderecoBean();
	private List<SelectItem> estados;
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public void setEnderecoBean(EnderecoBean enderecoBean) {
		this.enderecoBean = enderecoBean;
	}
	
	public List<SelectItem> getEstados() {
		estados = daoPessoa.listaEstados();
		return estados;
	}

	public String salvar() {
		pessoa.setEndereco(endereco);
		pessoa = daoGeneric.salvarEntity(pessoa);
		enderecoBean.salvarEndereco(endereco, pessoa);
		carregarPessoas();
		getMsg("Cadastrado com sucesso!");
		return "";
	}
	
	public void novo() {
		pessoa = new Pessoa();
		endereco = new Endereco();
	}
	
	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	public Pessoa editar() {
		
		if (pessoa.getEndereco() == null) {
			endereco = new Endereco();
		}
		else {
			endereco = enderecoBean.buscarEndereco(pessoa);
		}
		
		return pessoa;
	}
	
	public void delete() {
		daoGeneric.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		getMsg("Removido com sucesso!");
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
	
	public String deslogar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("userLogado");
		
		HttpServletRequest servletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		servletRequest.getSession().invalidate();
		
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
	
	public void getIdade(AjaxBehaviorEvent event) {

		LocalDate birthday = pessoa.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int idade = Period.between(birthday, LocalDate.now()).getYears();
		
		pessoa.setIdade(idade);

	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
			
			URL url = new URL("https://viacep.com.br/ws/"+ endereco.getCep() +"/json/");
			URLConnection connection = url.openConnection();
			
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while ((cep = bufferedReader.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			Endereco gson = new Gson().fromJson(jsonCep.toString(), Endereco.class);
			
			endereco.setCep(gson.getCep());
			endereco.setLogradouro(gson.getLogradouro());
			endereco.setBairro(gson.getBairro());
			endereco.setLocalidade(gson.getLocalidade());
			endereco.setUf(gson.getUf());
			
		} catch (Exception e) {
			e.printStackTrace();
			getMsg("Erro ao consultar o cep!");
		}
	}
	
}
