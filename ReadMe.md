# Read me

Completado: No

# Registro de Estudiantes y Carreras- Proyecto

## Descripción

Este proyecto implementa un sistema de registro de estudiantes y carreras, que permite gestionar información académica de los estudiantes en una universidad. El sistema incluye las funcionalidades para registrar estudiantes, matricularlos en carreras, y realizar diversas consultas a través de JPQL para obtener información relacionada con estudiantes y carreras.

## Características del sistema

Se implementaron las siguientes entidades en JPA:

- **Estudiante**:
    - dniEstudiante: String
    - apellido: String
    - nombre: String
    - genero: char
    - libretaUniversitaria: String
    - fechaNacimiento: LocalDate
    - Ciudad: String
    - inscripciones: List<Inscripcion>
- **Carrera**:
    - idCarrera: int
    - nombre: String
    - inscripciones: List<Inscripcion>
- **Inscripcion**:
    - id: inscripcionId
    - estudiante: Estudiante
    - carrera: Carrera
    - fechaInscripcion: Date
    - graduado: boolean

**El id de Inscripcion es Embebido:**

```java
@Embeddable
public class InscripcionId implements Serializable {
    private String dniEstudiante;
    private int idCarrera;
}
```

## Repositorios

**Interfaz Repository:**

```java
public interface Repository<Entity, ID extends Serializable> {
	public Entity findById(ID id);
	public void persist(Entity entity);
	public void delete(ID id);
	public List<Entity> findAll();
	public List<Entity> findWith(Condicion c);
	public List<Entity> findWith(Ordenamiento o);
	public List<Entity> findWith(Condicion c, Ordenamiento o);
}
```

**Clase base BaseJPARepository: métodos comunes a todos los repositorios.**

```java
public class BaseJPARepository <Entity, ID extends Serializable> implements Repository<Entity, ID> {
	protected EntityManager em;
	private Class<Entity> entityClass;
	private Class<ID> idClass;
	
	public BaseJPARepository(Class<Entity> entityClass, Class<ID> idClass) {...}

	@Override
	public Entity findById(ID id) {...}

	public boolean exist(ID id) {...}

	@Override
	public void persist(Entity entity){...}

	@Override
	public void delete(ID id) {...}

	@Override
	public List<Entity> findAll(){...}

	@Override
	public List<Entity> findWith(Condicion c) {...}
	
	@Override
	public List<Entity> findWith(Ordenamiento o) {...}

	@Override
	public List<Entity> findWith(Condicion c, Ordenamiento o) {...}
}
```

**Repositorio Estudiante: patrón Singleton.**

```java
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
```

**Repositorio Carrera: patrón Singleton.**

```java
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

	public List<ReporteCarreraDTO> obtenerListadoCarreras() {...}

}
```

Repositorio Inscripcion: patrón Singleton.

```java
public class InscripcionRepository extends BaseJPARepository<Inscripcion, InscripcionId>{
	private static volatile InscripcionRepository uniqueInstance;
    private InscripcionRepository() {
		super(Inscripcion.class,InscripcionId.class);
	}

	public static InscripcionRepository getInstance() {
		if (uniqueInstance == null) {
			synchronized (InscripcionRepository.class) {
				if (uniqueInstance == null)
					uniqueInstance = new InscripcionRepository();
			}
		}
		return uniqueInstance;
	}
    
  public List<Carrera> getCarreras(Condicion c, Ordenamiento o) {...}

  public List<Estudiante> getEstudiantes(Condicion c, Ordenamiento o) {...}

	public void save (Inscripcion inscripcion){...}

	public boolean exist(InscripcionId id) {...}
}
```

## Base de Datos

Se creó una base de datos PostgreSQL en el servidor Localhost, puerto 5432:

```java
<property name="javax.persistence.jdbc.driver" 
value="org.postgresql.Driver" />
<property name="javax.persistence.jdbc.url" 
value="jdbc:postgresql://localhost:5432/postgres" />
<property name="javax.persistence.jdbc.user" 
value="postgres" />
<property name="javax.persistence.jdbc.password" 
value="postgres" />
```

## Patrón Strategy: condiciones y ordenamientos

```java
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
    }

    public class CondicionEstPorCiudad implements Condicion{
        private String ciudad;
        @Override
        public String getCondicion() {
            return "WHERE e.ciudad = " + ciudad;
        }
    }

}
```

```java
public interface Ordenamiento {
    String getOrdenamiento();

    @AllArgsConstructor
    public class OrdenamientoSimple implements Ordenamiento{
        private String atributo;
        @Override
        public String getOrdenamiento(){
            String ordenamiento = "ORDER BY ".concat(atributo);
            return ordenamiento;
        }
    }

    /*
    @AllArgsConstructor
    public class OrdenamientoVacio implements Ordenamiento{
        @Override
        public String getOrdenamiento(){
            return "";
        }
    }
    */

    public class OrdenamientoCantInscriptos implements Ordenamiento{
        @Override
        public String getOrdenamiento() {
            return "GROUP BY id_carrera" +
                    "ORDER BY COUNT(*) DESC;";
        }
    }

    public class OrdenamientoAlfabeticoYAnios implements Ordenamiento{
        @Override
        public String getOrdenamiento() {
            return "GROUP BY carrera, EXTRACT(YEAR FROM i.fechaInscripcion) " +
                    "ORDER BY carrera ASC, anio ASC;";
        }
    }
}
```

## Funcionalidades

El sistema permite realizar las siguientes operaciones:

1. **Dar de alta un estudiante**: Registro de un nuevo estudiante con la información completa.
    
    Se utiliza el método 
    `public void persist(Entity entity){...}`
    
    del repositorio base BaseJPARepository con un parámetro Estudiante, que es el objeto a añadir.
    
2. **Matricular un estudiante en una carrera**: Registrar una inscripción.
    
    Se utiliza el método 
    `public void persist(Entity entity){...}`
    
    del repositorio base BaseJPARepository con un parámetro Inscripción, que es el objeto a añadir.
    
3. **Recuperar todos los estudiantes**: Consulta para obtener la lista de todos los estudiantes, con algún criterio de ordenamiento (por ejemplo, por apellido o número de libreta universitaria).
    
    Se utiliza el método
    `public List<Entity> findWith(Ordenamiento o) {...}`
    
    del repositorio base BaseJPARepository con el ordenamiento elegido como parámetro.
    
4. **Recuperar un estudiante por número de libreta universitaria**: Búsqueda de un estudiante específico a partir de su número de libreta universitaria.
    
    Se utiliza el método
    
    `public List<Entity> findWith(Condicion c) {...}`
    
    del repositorio base BaseJPARepository con la condición de que la libreta universitaria coincida con la dada.
    
    Condición pasada por parámetro:
    
    ```java
    public class CondicionLU implements Condicion{
       private String valorLU;
       @Override
       public String getCondicion(){
    		  String condicion = "WHERE e.libreta_universitaria = " + valorLU;
          return condicion;
       }
    }
    ```
    
5. **Recuperar estudiantes por género**: Filtrar la lista de estudiantes de acuerdo a su género (masculino, femenino, otro).
    
    Se utiliza el método
    
    `public List<Entity> findWith(Condicion c) {...}`
    
    del repositorio base BaseJPARepository con la condición de genero:
    
    ```java
    public class CondicionGenero implements Condicion{
        private char genero;
        @Override
        public String getCondicion(){
           String condicion = "WHERE e.genero = " + genero;
           return condicion;
        }
    }
    ```
    
6. **Recuperar carreras con estudiantes inscriptos**: Consulta para obtener todas las carreras con estudiantes activos, ordenadas por la cantidad de inscriptos.
    
    Se utiliza el método
    `public List<Carrera> getCarreras(Condicion c, Ordenamiento o) {...}`
    
    con la condición vacía y el ordenamiento de cantidad de inscriptos:
    
    ```java
    public class OrdenamientoCantInscriptos implements Ordenamiento{
        @Override
        public String getOrdenamiento() {
        return "GROUP BY id_carrera" +
          "ORDER BY COUNT(*) DESC;";
        }
    }
    ```
    
7. **Recuperar estudiantes de una carrera filtrados por ciudad de residencia**: Filtrado de estudiantes de una carrera específica que residan en una ciudad determinada:
    
    Se utiliza el método
    `public List<Estudiante> getEstudiantes(Condicion c, Ordenamiento o) {...}`
    
    con la condición de carrera y ciudad especificas:
    
    ```java
    public class CondicionEstPorCarreraYCiudad implements Condicion{
            private int idCarrera;
            private String ciudad;
    
            @Override
            public String getCondicion() {
                return "WHERE i.id_carrera = " + idCarrera +
                        "and i.dni_estudiante in " +
                        "(SELECT e.dni_estudiante from Estudiate e where e.ciudad = " + ciudad;
            }
    }
    ```
    
8. **Generar un reporte de carreras**: Reporte que incluye información sobre los estudiantes inscriptos y egresados por año, ordenado alfabéticamente por carrera y cronológicamente por año.
    
    La consulta implementada en CarreraRepository:
    
    ```java
    public List<ReporteCarreraDTO> obtenerListadoCarreras() {
    		String sql = "SELECT c.nombre AS nombreCarrera, " +
    				"i.fecha_inscripcion AS fechaInscripcion, " +
    				"COUNT(i.dni_estudiante) AS inscriptos, " +
    				"SUM(CASE WHEN i.graduado = true THEN 1 ELSE 0 END) AS egresados " +
    				"FROM Carrera c " +
    				"LEFT JOIN Inscripcion i ON c.id_carrera = i.id_carrera " +
    				"GROUP BY c.nombre, YEAR(i.fecha_inscripcion) " +
    				"ORDER BY c.nombre ASC, YEAR(i.fecha_inscripcion) ASC";
    
    		Query query = em.createNativeQuery(sql, ReporteCarreraDTO.class);
    		return query.getResultList();
    	}
    ```
    
    El objeto DTO creado para entregar el reporte:
    
    ```java
    public class ReporteCarreraDTO {
        private String nombreCarrera;
        private int anioInscripcion;
        private long inscriptos;
        private long egresados;
    
        public ReporteCarreraDTO(String nombreCarrera, Date fechaInscripcion, long inscriptos, long egresados) {
            this.nombreCarrera = nombreCarrera;
            this.anioInscripcion = fechaInscripcion.getYear();
            this.inscriptos = inscriptos;
            this.egresados = egresados;
        }
    }
    ```
    

## Diagrama de objetos y DER

Los diagramas de objetos y DER están incluidos en la carpeta `/diagrams`. Estos representan las entidades y relaciones modeladas en el sistema:

- **Diagrama de objetos**: `data/Dclases.png`
- **Diagrama DER**: `data/Der.png`

---

### Integrantes

- **Estudiantes:** Girardi Emiliana, Delgado Lucia, Villa Eliseo, Palacios Joaquín.
- **Materia**: Diseño de Sistemas de Software
- **Fecha de entrega**: 26/09/2024
