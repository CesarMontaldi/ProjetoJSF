package br.com.cesarmontaldi;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoEndereco;
import br.com.cesarmontaldi.repository.DaoEnderecoImpl;
import jakarta.persistence.EntityManager;


@javax.faces.view.ViewScoped
@Named("enderecoBean")
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	private Pessoa pessoa = new Pessoa();
	private Endereco endereco = new Endereco();
	private DaoEndereco daoEndereco = new DaoEnderecoImpl();

	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
		endereco = daoEndereco.salvarEndereco(endereco);

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
