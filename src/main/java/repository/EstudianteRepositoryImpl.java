package repository;

import entity.Estudiante;
import lombok.AllArgsConstructor;
import condiciones.Condicion;
import condiciones.Ordenamiento;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


@AllArgsConstructor
public class EstudianteRepositoryImpl implements EstudianteRepository {

    private EntityManager em;

    @Override
    public Estudiante darAltaEstudiante(Estudiante e) { //
        if(em.find(Estudiante.class,e.getDniEstudiante())==null) {
            em.persist(e);
        } else {
            e = em.merge(e);
        }
        return e;
    }

    @Override
    public List<Estudiante> getEstudiantes(Condicion c, Ordenamiento ord) {
        String query = "SELECT e FROM Estudiante e".concat(c.getCondicion()).concat(ord.getOrdenamiento());
        TypedQuery<Estudiante> q = em.createQuery(query, Estudiante.class);
        return q.getResultList();
    }


}
