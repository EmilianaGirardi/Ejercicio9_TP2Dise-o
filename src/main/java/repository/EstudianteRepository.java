package repository;

import entity.Estudiante;
import condiciones.Condicion;
import condiciones.Ordenamiento;
import java.util.List;

public interface EstudianteRepository {
    //dar de alta un estudiante
    //recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple. (2)
    //recuperar un estudiante, en base a su número de libreta universitaria.
    //recuperar todos los estudiantes, en base a su género.

    //al usar una consulta que devuelve una lista de estudiantes con los resultados de una consulta con su condicion y ordenamiento eso resuelve los pedidos de 2 3 y 4 en un solo metodo con 2 strategy
    Estudiante darAltaEstudiante(Estudiante e);
    List<Estudiante> getEstudiantes(Condicion c, Ordenamiento ord);
}
