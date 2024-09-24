package condiciones;

import lombok.AllArgsConstructor;

public interface Condicion {
    String getCondicion();

    
    @AllArgsConstructor
    public class CondicionLU implements Condicion{
        private String valorLU;
        @Override
        public String getCondicion(){
            String condicion = "WHERE e.libreta_universitaria = " + valorLU;
            return condicion;
        }
    }

    @AllArgsConstructor
    public class CondicionGenero implements Condicion{
        private char genero;
        @Override
        public String getCondicion(){
            String condicion = "WHERE e.genero = " + genero;
            return condicion;
        }
    }

    @AllArgsConstructor
    public class CondicionVacia implements Condicion{
        @Override
        public String getCondicion(){
            return "";
        }
    }


    @AllArgsConstructor
    public class CondicionEstPorCarreraYCiudad implements Condicion{
        private int idCarrera;
        private String ciudad;

        @Override
        public String getCondicion() {
            return "WHERE i.id_carrera = " + idCarrera +
                    "and i.dni_estudiante in " +
                    "(SELECT e.dni_estudiante from Estudiate e where e.ciudad = " + ciudad;
        }

        /* en InscripcionRepository:

        public List<Estudiante> getEstudiante(Condicion c, Ordenamiento o) {
            String query = "SELECT i.dni_estudiante FROM Inscripcion i".concat(c.getCondicion()).concat(ord.getOrdenamiento());
            TypedQuery<Inscripcion> q = em.createQuery(query, Estudiante.class);
            List<String> result = q.getResultList();
            List<Estudiante> estudiantes = new List<Estudiante>();
            for each (dni in result){
                estudiantes.add(EstudianteRepository.getEstudianteById(dni);
            }
            return estudiantes;
        }
         */

    }

    public class CondicionEstPorCiudad implements Condicion{
        private String ciudad;
        @Override
        public String getCondicion() {
            return "WHERE e.ciudad = " + ciudad;
        }
    }
}


