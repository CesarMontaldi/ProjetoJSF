package br.com.cesarmontaldi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
	

	public Endereco salvarEndereco(Endereco endereco, Pessoa pessoa) {
		
		endereco.setUsuario(pessoa);
		endereco = daoGeneric.salvarEntity(endereco);
		
		return endereco;
	}

	public List <Endereco> carregarEnderecos() {
		
		List <Endereco> enderecos = (List<Endereco>) daoEndereco.consultarEnderecos();
		
		return enderecos;
	}
	
	
	public Endereco buscarEndereco(Pessoa pessoa) {
		Pessoa user = pessoa;
		endereco = (Endereco) daoEndereco.consultaEndereco(user.getId());
		
		return endereco;
	}

	
}
