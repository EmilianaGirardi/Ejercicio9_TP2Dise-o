package repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import condiciones.Condicion;
import condiciones.Ordenamiento;

public class BaseJPARepository <Entity, ID extends Serializable> implements Repository<Entity, ID> {
	protected EntityManager em;
	private Class<Entity> entityClass;
	private Class<ID> idClass;
	
	public BaseJPARepository(Class<Entity> entityClass, Class<ID> idClass) {
		super();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Project");
		this.em = emf.createEntityManager();
		this.entityClass = entityClass;
		this.idClass = idClass;
	}

	@Override
	public Entity findById(ID id) {
		Entity e = em.find(entityClass, id);
		return e;
	}

	@Override
	public void persist(Entity entity) {
		em.getTransaction().begin();
		try {
			em.persist(entity);
		} catch (EntityExistsException e) {
			em.merge(entity);
		}
		em.getTransaction().commit();
	}

	@Override
	public void delete(ID id) {
		Entity e = em.find(entityClass, id);
		if (e!=null) {
			em.getTransaction().begin();
			em.remove(e);
			em.getTransaction().commit();	
		}
	}

	@Override
	public List<Entity> findAll(){
		TypedQuery<Entity> q = em.createQuery("SELECT e FROM Entity e",entityClass);
		return q.getResultList();
	}

	@Override
	public List<Entity> findWith(Condicion c) {
		TypedQuery<Entity> q = em.createQuery("SELECT e FROM Entity e ".concat(c.getCondicion()),entityClass);
		return q.getResultList();
	}
	
	@Override
	public List<Entity> findWith(Ordenamiento o) {
		TypedQuery<Entity> q = em.createQuery("SELECT e FROM Entity e ".concat(o.getOrdenamiento()),entityClass);
		return q.getResultList();
	}

	@Override
	public List<Entity> findWith(Condicion c, Ordenamiento o) {
		TypedQuery<Entity> q = em.createQuery("SELECT e FROM Entity e ".concat(c.getCondicion()).concat(o.getOrdenamiento()),entityClass);
		return q.getResultList();
	}

}
