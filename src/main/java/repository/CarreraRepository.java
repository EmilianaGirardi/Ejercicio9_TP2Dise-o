package repository;

import entity.Carrera;

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
	

}
