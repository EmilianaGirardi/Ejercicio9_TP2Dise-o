package repository;

import entity.Carrera;

import javax.persistence.EntityManager;

public class CarreraRespositoryImpl implements CarreraRepository{
    private EntityManager em;

    public CarreraRespositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Carrera carrera) {
        if(em.find(Carrera.class, carrera.getIdCarrera())==null) {
            em.persist(carrera);
        } else {
            carrera = em.merge(carrera);
        }
    }

    @Override
    public boolean deleteById(int id) {
        Carrera eliminar = this.getCarreraById(id);
        if (eliminar != null) {
            em.remove(eliminar);
            return true;
        }
        return false;
    }

    @Override
    public Carrera getCarreraById(int id) {
        return em.find(Carrera.class,id); // Busca y devuelve una instancia (SELECT) a partir del identificador (id)
    }
}
