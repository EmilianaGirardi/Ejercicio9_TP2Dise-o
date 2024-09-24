package repository;

import embebido.InscripcionId;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;
import condiciones.Condicion;
import condiciones.Ordenamiento;


import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
		/*TypedQuery<Carrera> q = em.createQuery("SELECT c FROM inscripcion i JOIN carrera c ".concat(c.getCondicion()).concat(" GROUP BY c.idCarrera ").concat(o.getOrdenamiento()), Carrera.class);
		return q.getResultList();
		*/
		String query = "SELECT i.id_carrera FROM Inscripcion i".concat(c.getCondicion()).concat(o.getOrdenamiento());
		TypedQuery<Integer> q = em.createQuery(query, Integer.class);
		List<Integer> result = q.getResultList(); //lista de id_carrera
		List<Carrera> carreras = new ArrayList<>();
		for (Integer id: result){
			carreras.add(CarreraRepository.getInstance().findById(id));
		}
		return carreras;
	}

    public List<Estudiante> getEstudiantes(Condicion c, Ordenamiento o) {
    	/*String query = "SELECT e FROM inscripcion i JOIN estudiante e ".concat(c.getCondicion());
        TypedQuery<Estudiante> q = em.createQuery(query,Estudiante.class);
        return q.getResultList();
    	 */
		String query = "SELECT i.dni_estudiante FROM Inscripcion i".concat(c.getCondicion()).concat(ord.getOrdenamiento());
		TypedQuery<String> q = em.createQuery(query, String.class);
		List<String> result = q.getResultList();
		List<Estudiante> estudiantes = new ArrayList<>();
		for (String dni: result){
			estudiantes.add(EstudianteRepository.getInstance().findById(dni));
		}
		return estudiantes;
    }
}
