package repository;

import condiciones.Condicion;
import condiciones.Ordenamiento;
import embebido.InscripcionId;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;

import java.util.List;

public interface InscripcionRepository {
    //matricular un estudiante en una carrera
    //recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    //recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    public void save(Inscripcion inscripcion);

    public boolean deleteById(InscripcionId id);

    public List<Inscripcion> getInscripciones(Condicion c, Ordenamiento o);

    public List<Carrera> getCarreras(Condicion c, Ordenamiento o);

    public List<Estudiante> getEstudiante(Condicion c, Ordenamiento o);
}
