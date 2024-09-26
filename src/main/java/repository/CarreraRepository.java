package repository;

import dto.ReporteCarreraDTO;
import entity.Carrera;

import javax.persistence.Query;
import java.util.ArrayList;
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
		String sql = "SELECT new dto.ReporteCarreraDTO(c.nombre, " +
				"EXTRACT(YEAR FROM i.fechaInscripcion), " +
				"COUNT(i.estudiante.dniestudiante), " +
				"SUM(CASE WHEN i.graduado = true THEN 1 ELSE 0 END)) " +
				"FROM Carrera c " +
				"INNER JOIN Inscripcion i ON c.idcarrera = i.carrera.idcarrera " +
				"GROUP BY c.nombre, EXTRACT(YEAR FROM i.fechaInscripcion) " +
				"ORDER BY c.nombre ASC, EXTRACT(YEAR FROM i.fechaInscripcion) ASC";

		Query query = em.createQuery(sql, ReporteCarreraDTO.class);
		return query.getResultList();
	}

}
