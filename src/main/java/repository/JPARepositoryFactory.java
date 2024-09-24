package repository;

public class JPARepositoryFactory implements RepositoryFactory {
	private static volatile JPARepositoryFactory uniqueInstance;
	private JPARepositoryFactory() {}
	
	public static JPARepositoryFactory getInstance() {
		if (uniqueInstance == null) {
			synchronized (JPARepositoryFactory.class) {
				if (uniqueInstance == null)
					uniqueInstance = new JPARepositoryFactory();
			}
		}
		return uniqueInstance;
	}
	
	@Override
	public EstudianteRepository getEstudianteRepository() {
		return EstudianteRepository.getInstance();
	}

	@Override
	public CarreraRepository getCarreraRepository() {
		return CarreraRepository.getInstance();
	}

	@Override
	public InscripcionRepository getInscripcionRepository() {
		return InscripcionRepository.getInstance();
	}

}
