# Read me ej 9 tp2

Completado: No

- dar de alta un estudiante

En EstudianteRepository: 
`public Estudiante darAltaEstudiante(Estudiante e);`

- matricular un estudiante en una carrera

En IncripcionRepository:
`public void save(Inscripcion inscripcion);`

- recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.

En EstudianteRepository:

`public List<Estudiante> getEstudiantes(Condicion c, Ordenamiento ord)`

- recuperar un estudiante, en base a su número de libreta universitaria.

Se implementó la siguiente condición:

```java
public class CondicionLU implements Condicion{
    private String valorLU;
    @Override
    public String getCondicion(){
       String condicion = "WHERE e.libretaUniversitaria = ".concat('"'+ valorLU + '"');
       return condicion;
    }
}
```

- recuperar todos los estudiantes, en base a su género.

Se implementó la siguiente condición:

```java
public class CondicionGenero implements Condicion{
     private char genero;
     @Override
     public String getCondicion(){
         String condicion = "WHERE e.genero = ".concat('"'+ String.valueOf(genero) + '"');
         return condicion;
     }
 }
```

- recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.

Seria un getCarreras en InscripcionRepository, sin condicion y ordenandolos por cantidad de inscriptos.

Se implementó la siguiente condición:

```java
public class OrdenamientoCantInscriptos implements Ordenamiento{
        @Override
        public String getOrdenamiento() {
            return "GROUP BY id_carrera" +
                    "ORDER BY COUNT(*) DESC;";
        }
}
```

En InscripcionRepository:

```java
public List<Carrera> getCarreras(Condicion c, Ordenamiento o){
        String query = "SELECT i.id_carrera FROM Inscripcion i".concat(c.getCondicion()).concat(ord.getOrdenamiento());
            TypedQuery<Inscripcion> q = em.createQuery(query, Estudiante.class);
            List<String> result = q.getResultList(); //lista de id_carrera
            List<Carrera> carreras = new List<Carrera>();
            for each (id in result){
                careras.add(CarreraRepository.getById(id);
            }
            return carreras;
        }
}
```

- recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.

Se implementó la siguiente condición:

```java
public class CondicionEstPorCarreraYCiudad implements Condicion{
        private int idCarrera;
        private String ciudad;

        @Override
        public String getCondicion() {
            return "WHERE i.idCarrera = " + idCarrera +
                    "and i.dniEstudiante in " +
                    "(SELECT e.dniEstudiante from Estudiate e where e.ciudad = " + ciudad;
        }
}
```

En InscripcionRepository:

```java
    String query = "SELECT i.dniEstudiante FROM Inscripcion i".concat(c.getCondicion()).concat(ord.getOrdenamiento());
            TypedQuery<Inscripcion> q = em.createQuery(query, Estudiante.class);
            List<String> result = q.getResultList();
            List<Estudiante> estudiantes = new List<Estudiante>();
            for each (dni in result){
                estudiantes.add(EstudianteRepository.getEstudianteById(dni);
            }
            return estudiantes;
        }
```
