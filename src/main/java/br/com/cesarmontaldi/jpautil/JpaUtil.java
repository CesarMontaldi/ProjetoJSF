package br.com.cesarmontaldi.jpautil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JpaUtil {
	
	private EntityManagerFactory factory = null;
	
	public JpaUtil() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("ProjetoJSF");
		}
	}
	
	@Produces
	@ApplicationScoped
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public Object getPrimaryKey(Object entity) {
		
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	} 
}
