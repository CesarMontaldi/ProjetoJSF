package br.com.cesarmontaldi.ProjetoJSF;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "pessoaBean")
public class PessoaBean {
	
	private String nome;
	private String sobreNome;
	private String nomeCompleto;
	
	public void mostrarNome() {
		nomeCompleto = nome + " " + sobreNome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
}
