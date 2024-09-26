package repository;

import dto.ReporteCarreraDTO;
import entity.Carrera;

import javax.persistence.Query;
import java.util.List;

public class CarreraRepository extends BaseJPARepository<Carrera, Integer>{
	private static volatile CarreraRepository uniqueInstance;
    private CarreraRepository() {
        super(Carrera.class, Integer.class);
    }
    
	public static CarreraRepository getInstance() {
		if (uniqueInstance == null) {
			synchronized (CarreraRepository.class) {
				if (uniqueInstance == null)
					uniqueInstance = new CarreraRepository();
			}
		}
		return uniqueInstance;
	}

	public List<ReporteCarreraDTO> obtenerListadoCarreras() {
		String sql = "SELECT c.nombre AS nombreCarrera, " +
				"i.fecha_inscripcion AS fechaInscripcion, " +
				"COUNT(i.dni_estudiante) AS inscriptos, " +
				"SUM(CASE WHEN i.graduado = true THEN 1 ELSE 0 END) AS egresados " +
				"FROM Carrera c " +
				"LEFT JOIN Inscripcion i ON c.id_carrera = i.id_carrera " +
				"GROUP BY c.nombre, YEAR(i.fecha_inscripcion) " +
				"ORDER BY c.nombre ASC, YEAR(i.fecha_inscripcion) ASC";

		Query query = em.createNativeQuery(sql, ReporteCarreraDTO.class);
		return query.getResultList();
	}

}
