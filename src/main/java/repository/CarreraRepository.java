package repository;

import entity.Carrera;

public interface CarreraRepository{
    public void save(Carrera carrera);

    public boolean deleteById(int id);

    public Carrera getCarreraById(int id);


}
