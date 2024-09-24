package repository;

import java.io.Serializable;
import java.util.List;

import condiciones.Condicion;
import condiciones.Ordenamiento;

public interface Repository<Entity, ID extends Serializable> {
	public Entity findById(ID id);
	public void persist(Entity entity);
	public void delete(ID id);
	public List<Entity> findAll();
	public List<Entity> findWith(Condicion c);
	public List<Entity> findWith(Ordenamiento o);
	public List<Entity> findWith(Condicion c, Ordenamiento o);
}
