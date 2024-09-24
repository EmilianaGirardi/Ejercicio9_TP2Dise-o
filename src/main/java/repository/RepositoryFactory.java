package repository;

public interface RepositoryFactory {
	EstudianteRepository getEstudianteRepository();
	CarreraRepository getCarreraRepository();
	InscripcionRepository getInscripcionRepository();
	//... otros si los hubiera
}
