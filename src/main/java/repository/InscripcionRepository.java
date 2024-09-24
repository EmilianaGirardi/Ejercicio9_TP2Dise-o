package repository;

import embebido.InscripcionId;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;
import condiciones.Condicion;
import condiciones.Ordenamiento;


import javax.persistence.TypedQuery;
import java.util.List;


public class InscripcionRepository extends BaseJPARepository<Inscripcion, InscripcionId>{
	private static volatile InscripcionRepository uniqueInstance;
    private InscripcionRepository() {
		super(Inscripcion.class,InscripcionId.class);
	}

	public static InscripcionRepository getInstance() {
		if (uniqueInstance == null) {
			synchronized (InscripcionRepository.class) {
				if (uniqueInstance == null)
					uniqueInstance = new InscripcionRepository();
			}
		}
		return uniqueInstance;
	}
    
    public List<Carrera> getCarreras(Condicion c, Ordenamiento o) {
		TypedQuery<Carrera> q = em.createQuery("SELECT c FROM inscripcion i JOIN carrera c ".concat(c.getCondicion()).concat(" GROUP BY c.idCarrera ").concat(o.getOrdenamiento()), Carrera.class);
		return q.getResultList();
	}

    public List<Estudiante> getEstudiantes(Condicion c, Ordenamiento o) {
    	String query = "SELECT e FROM inscripcion i JOIN estudiante e ".concat(c.getCondicion());
        TypedQuery<Estudiante> q = em.createQuery(query,Estudiante.class);
        return q.getResultList();
    }
}
