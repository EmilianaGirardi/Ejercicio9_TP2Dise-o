package repository;

import entity.Estudiante;

public class EstudianteRepository extends BaseJPARepository<Estudiante, String> {
	private static volatile EstudianteRepository uniqueInstance;
	private EstudianteRepository() {
		super(Estudiante.class,String.class);
	}
	
	public static EstudianteRepository getInstance() {
		if (uniqueInstance == null) {
			synchronized (EstudianteRepository.class) {
				if (uniqueInstance == null)
					uniqueInstance = new EstudianteRepository();
			}
		}
		return uniqueInstance;
	}
	
	
}
