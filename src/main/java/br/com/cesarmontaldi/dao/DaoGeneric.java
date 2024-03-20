package br.com.cesarmontaldi.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@Named
public class DaoGeneric<Entity> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private JpaUtil jpaUtil;
	
	public DaoGeneric() {
		
	}

	
	public void salvar(Entity entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.persist(entity);
		
		transaction.commit();

	}
	
	public Entity salvarEntity(Entity entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Entity newEntity = entityManager.merge(entity);
		
		transaction.commit();
		
		return newEntity;

	}
	
	
	public List<Entity> getListEntity(Class<Entity> entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<Entity> entityList = entityManager.createQuery(" from " + entity.getName() + " order by id ASC ").getResultList();
		
		transaction.commit();
		
		
		return entityList;
	}
	
	public Entity buscar(Entity entity, String idUser) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		
		Entity findEntity = (Entity) entityManager.find(entity.getClass(), idUser);
		
		transaction.commit();

		return findEntity;

	}
	
	public void delete(Entity entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.remove(entity);
		
		transaction.commit();

	}
	
	public void deletePorId(Entity entity) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Object id = jpaUtil.getPrimaryKey(entity);
		entityManager.createQuery("delete from " + entity.getClass().getSimpleName() + " where id = " + id).executeUpdate();
		
		transaction.commit();

	}

}
