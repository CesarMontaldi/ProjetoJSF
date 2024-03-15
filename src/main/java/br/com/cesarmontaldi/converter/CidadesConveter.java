package br.com.cesarmontaldi.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import br.com.cesarmontaldi.model.Cidades;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadesConveter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override /* Retorna o Objeto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		if (codigoCidade.equalsIgnoreCase("Selecione")) {
			return null;
		}
		else {
			Cidades cidade = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));
			return cidade;
		}
	}

	@Override /* Retorna apenas o código do Objeto em String */
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
		
		if (cidade == null) {
			return null;
		}
		
		if (cidade instanceof Cidades) {
			return ((Cidades) cidade).getId().toString();
		}
		
		else {
			return cidade.toString();
		}
	}

}
