package repository;

import embebido.InscripcionId;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;
import condiciones.Condicion;
import condiciones.Ordenamiento;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class InscripcionRepositoryImpl implements InscripcionRepository{
    private EntityManager em;

    public InscripcionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Inscripcion inscripcion) {
        em.persist(inscripcion);
    }

    @Override
    public boolean deleteById(InscripcionId id) {
        return false;
    }

    @Override
    public List<Inscripcion> getInscripciones(Condicion c, Ordenamiento o) {
        TypedQuery<Inscripcion> q = em.createQuery("SELECT i FROM Inscripcion i",Inscripcion.class); //entidad
        //List<Inscripcion> inscripciones = q.getResultList();

        /* return inscripciones.stream()
                .filter(inscripcion -> c.cumple(inscripcion))
                .sorted((i1,i2) -> o.ordenar(i1,i2))
                .collect(Collectors.toList());
*/
        return null;
    }

    @Override
    public List<Carrera> getCarreras(Condicion c, Ordenamiento o) {

        return null;
    }

    @Override
    public List<Estudiante> getEstudiante(Condicion c, Ordenamiento o) {
        return null;
    }
}
