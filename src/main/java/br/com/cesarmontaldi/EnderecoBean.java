package br.com.cesarmontaldi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoEndereco;
import br.com.cesarmontaldi.repository.DaoEnderecoImpl;


@ViewScoped
@ManagedBean(name = "enderecoBean")
public class EnderecoBean {
	
	private Endereco endereco = new Endereco();
	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Endereco> daoGeneric = new DaoGeneric<Endereco>();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private DaoEndereco daoEndereco = new DaoEnderecoImpl();

	
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public DaoGeneric<Endereco> getDaoGeneric() {
		return daoGeneric;
	}
	
	public void setDaoGeneric(DaoGeneric<Endereco> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public DaoEndereco getDaoEndereco() {
		return daoEndereco;
	}
	
	public void setDaoEndereco(DaoEndereco daoEndereco) {
		this.daoEndereco = daoEndereco;
	}
	
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	

	public void carregarEnderecos() {
		
		Pessoa user = pessoa.getUserLogado();
		enderecos = daoEndereco.consultarEndereco(user.getId());

	}
	
	public void salvarEndereco() {
		
		Pessoa user = pessoa.getUserLogado();
		endereco.setUsuario(user);
		endereco = daoGeneric.salvarEntity(endereco);
		carregarEnderecos();
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
	
	public void getMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
	
}
