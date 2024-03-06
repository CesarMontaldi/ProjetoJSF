package br.com.cesarmontaldi.dao;

import java.util.List;

import br.com.cesarmontaldi.jpautil.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DaoGeneric<Entity> {
	
	public void salvar(Entity entity) {
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.persist(entity);
		
		transaction.commit();
		entityManager.close();

	}
	
	public Entity salvarEntity(Entity entity) {
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Entity newEntity = entityManager.merge(entity);
		
		transaction.commit();
		entityManager.close();
		
		return newEntity;

	}
	
	public void delete(Entity entity) {
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.remove(entity);
		
		transaction.commit();
		entityManager.close();

	}
	
	public List<Entity> getListEntity(Class<Entity> entity) {
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<Entity> entityList = entityManager.createQuery(" from " + entity.getName() + " order by id ASC ").getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return entityList;
	}
	
	
	public void deletePorId(Entity entity) {
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Object id = JpaUtil.getPrimaryKey(entity);
		entityManager.createQuery("delete from " + entity.getClass().getSimpleName() + " where id = " + id).executeUpdate();
		
		transaction.commit();
		entityManager.close();

	}

}
