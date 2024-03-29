package br.com.cesarmontaldi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity 
public class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@NotBlank(message = "Campo sobrenome deve ser informado")
	private String sobrenome;
	private String login;
	private String senha;
	private Integer idade;
	private String perfil;
	private String sexo;
	private Boolean ativo;
	
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	private Endereco endereco;
	
	@ManyToOne
	private Cidades cidades;
	
	@Transient /* Não fica perssistente / não grava no banco */
	private Estados estados;
	
	@Column(columnDefinition = "text") /* Tipo text grava arquivos em base64 */
	private String fotoUserMin;
	
	private String extensao;
	
	@Lob /* Grava arquivos no banco */
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotoUser;
	
	
	public Pessoa() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Estados getEstados() {
		return estados;
	}
	
	public void setEstados(Estados estados) {
		this.estados = estados;
	}

	public Cidades getCidades() {
		return cidades;
	}

	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}
	
	public String getFotoUserMin() {
		return fotoUserMin;
	}

	public void setFotoUserMin(String fotoUserMin) {
		this.fotoUserMin = fotoUserMin;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public byte[] getFotoUser() {
		return fotoUser;
	}

	public void setFotoUser(byte[] fotoUser) {
		this.fotoUser = fotoUser;
	}

	
	public Pessoa getUserLogado() {
		// retorna o usuario logado
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa user = (Pessoa) externalContext.getSessionMap().get("userLogado");
		
		return user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(id, other.id);
	}
	
}
