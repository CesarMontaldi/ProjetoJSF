package br.com.cesarmontaldi;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.cesarmontaldi.dao.DaoGeneric;
import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Cidades;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.Estados;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.repository.DaoPessoa;
import jakarta.persistence.EntityManager;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;

@javax.faces.view.ViewScoped
@Named("pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa pessoa = new Pessoa();
	private Endereco endereco = new Endereco();
	private EnderecoBean enderecoBean = new EnderecoBean();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private Part arquivoFoto;
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	
	@Inject
	private DaoPessoa daoPessoa;
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private JpaUtil jpaUtil;
	
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
	
	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}
	
	public Part getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(Part arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}
	
	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	/* Metodo que converte InputStream em um array de Bytes */
	private byte[] getByte(InputStream inStream) throws IOException {
		
		int length; 
		int size = 1024;
		byte[] buf = null;
		
		if (inStream instanceof ByteArrayInputStream) {
			size = inStream.available();
			buf = new byte[size];
			length = inStream.read(buf, 0, size);
		}
		else {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			buf = new byte[size];
			
			while ((length = inStream.read(buf, 0, size)) != -1) {
				outStream.write(buf, 0, length);
			}
			
			buf = outStream.toByteArray();
		}
		
		return buf;
	} 
	
	public String salvar() throws IOException {
		
		if (arquivoFoto != null) {
			/* Processa a Imagem */
			byte[] imagemByte = getByte(arquivoFoto.getInputStream());
			pessoa.setFotoUser(imagemByte); /* Salva a foto em tamanho original */
			
			/* Transforma em BufferImage */
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
			
			/* Pega o tipo da imagem */
			int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			
			int largura = 200;
			int altura = 200;
			
			/* Cria a miniatura da foto */
			BufferedImage resizedImage = new BufferedImage(largura, altura, type);
			Graphics2D graphics2d = resizedImage.createGraphics();
			graphics2d.drawImage(bufferedImage, 0, 0, largura, altura, null);
			graphics2d.dispose();
			
			/* Escrever novamente a imagem em tamanho menor */
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			String extensao = arquivoFoto.getContentType().split("\\/")[1];
			ImageIO.write(resizedImage, extensao, outStream);
			
			String miniImagem = "data:" + arquivoFoto.getContentType() +";base64," + DatatypeConverter.printBase64Binary(outStream.toByteArray());
			
			/* Processa a Imagem */
			pessoa.setFotoUserMin(miniImagem);
			pessoa.setExtensao(extensao);
		}
		
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
		
		if (pessoa.getEndereco() != null) {
			endereco = pessoa.getEndereco();
		}
		
		if (pessoa.getCidades() != null) {
			Estados estado = pessoa.getCidades().getEstados();
			pessoa.setEstados(estado);
			
			List<Cidades> cidades = jpaUtil.getEntityManager().createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(selectItemsCidade);
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
			
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = request.getSession();
			
			session.setAttribute("userLogado", user);
			
			return "principal.jsf";
		}
		else {
			getMsg("Usuário não encontrado!");
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
	
	public void carregaCidades(AjaxBehaviorEvent event) {
		//String codigoEstado = (String) event.getComponent().getAttributes().get("submittedValue");
		
		Estados estado = (Estados) ((SelectOneMenu) event.getSource()).getValue();

		
		if (estado != null) {
	
			pessoa.setEstados(estado);
			
			List<Cidades> cidades = jpaUtil.getEntityManager().createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
			
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			
			for (Cidades cidade : cidades) {
				
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
				
			}
			
			setCidades(selectItemsCidade);
		}
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
		}
		
	}
	
	public void downloadFoto() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadId = params.get("fileDownloadId");
		
		Pessoa user = daoGeneric.buscar(pessoa, fileDownloadId);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addHeader("Content-Disposition", "attachment; filename=download." + user.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(user.getFotoUser().length);
		response.getOutputStream().write(user.getFotoUser());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
}
