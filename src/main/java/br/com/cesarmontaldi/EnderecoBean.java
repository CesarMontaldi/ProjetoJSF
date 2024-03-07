package br.com.cesarmontaldi;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoEndereco;
import br.com.cesarmontaldi.repository.DaoEnderecoImpl;


@ViewScoped
@ManagedBean(name = "enderecoBean")
public class EnderecoBean {
	
	private Endereco endereco = new Endereco();
	private DaoGeneric<Endereco> daoGeneric = new DaoGeneric<Endereco>();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private DaoEndereco daoEndereco = new DaoEnderecoImpl();
	private PessoaBean pessoaBean = new PessoaBean();
	
	
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
	
	public PessoaBean getPessoaBean() {
		return pessoaBean;
	}
	
	public void setPessoaBean(PessoaBean pessoaBean) {
		this.pessoaBean = pessoaBean;
	}
	
	
	private void carregarEnderecos() {
		
		Pessoa user = pessoaBean.getUserLogado();

		enderecos = daoEndereco.consultarEndereco(user.getId());
	}
	
	public void salvar() {
		
		Pessoa user = pessoaBean.getUserLogado();
		endereco.setUsuario(user);
		endereco = daoGeneric.salvarEntity(endereco);

		carregarEnderecos();
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		System.out.println("Metodo pesquisa cep chamado CEP: " + endereco.getCep());
	}
	
}
