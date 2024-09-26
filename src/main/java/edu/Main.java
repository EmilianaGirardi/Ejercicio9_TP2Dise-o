package edu;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import condiciones.*;
import embebido.InscripcionId;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;
import lombok.*;
import repository.*;

import javax.persistence.criteria.CriteriaBuilder;

public class Main {
	public static Estudiante crearEstudiante(String dni, String ap, String nom, String lu, char gen, LocalDate fn, String ciu){
		Estudiante e = new Estudiante();
		e.setDniEstudiante(dni);
		e.setApellido(ap);
		e.setNombre(nom);
		e.setLibretaUniversitaria(lu);
		e.setGenero(gen);
		e.setFechaNacimiento(fn);
		e.setCiudad(ciu);
		return e;
	}

	public static Inscripcion crearInscripcion(InscripcionId id, Estudiante e, Carrera c, Date f, boolean g){
		Inscripcion ins = new Inscripcion();
		ins.setId(id);
		ins.setEstudiante(e);
		ins.setCarrera(c);
		ins.setFechaInscripcion(f);
		ins.setGraduado(g);
		return ins;
	}

	public static Carrera crearCarrera(int id, String n){
		Carrera car = new Carrera();
		car.setIdCarrera(id);
		car.setNombre(n);
		return car;
	}

	public static void persistirEstudiantes(EstudianteRepository repEstudiante){
		List<Estudiante> estudiantes = List.of(
				crearEstudiante("1234", "Perez", "Juan", "1", 'M',LocalDate.of(2000,9,10),"Tandil"),
				crearEstudiante("2345", "Gómez", "Ana", "2345", 'F', LocalDate.of(2001, 5, 20), "Buenos Aires"),
				crearEstudiante("3456", "López", "Carlos", "3456", 'M', LocalDate.of(1999, 10, 12), "Córdoba"),
				crearEstudiante("4567", "Martínez", "Lucía", "4567", 'F', LocalDate.of(2000, 3, 5), "Rosario"),
				crearEstudiante("5678", "Pérez", "Javier", "5678", 'M', LocalDate.of(1998, 7, 30), "Mendoza"),
				crearEstudiante("6789", "Sánchez", "Mariana", "6789", 'F', LocalDate.of(2002, 1, 18), "La Plata"),
				crearEstudiante("7890", "Ramírez", "Pablo", "7890", 'M', LocalDate.of(2001, 9, 15), "Mar del Plata"),
				crearEstudiante("8901", "Torres", "Julieta", "8901", 'F', LocalDate.of(1997, 4, 10), "Salta"),
				crearEstudiante("9012", "Fernández", "Santiago", "9012", 'M', LocalDate.of(2000, 12, 22), "Neuquén"),
				crearEstudiante("0123", "González", "Paula", "0123", 'F', LocalDate.of(1999, 11, 3), "San Miguel de Tucumán"),
				crearEstudiante("1235", "Rodríguez", "Matías", "1234", 'M', LocalDate.of(1998, 6, 7), "Santa Fe"),
				crearEstudiante("2346", "Romero", "Camila", "2346", 'F', LocalDate.of(2001, 8, 14), "Posadas"),
				crearEstudiante("3457", "Alvarez", "Diego", "3457", 'M', LocalDate.of(2002, 2, 25), "San Juan"),
				crearEstudiante("4568", "Méndez", "Valentina", "4568", 'F', LocalDate.of(1997, 10, 28), "Río Cuarto"),
				crearEstudiante("5679", "Ruiz", "Tomás", "5679", 'M', LocalDate.of(2001, 12, 11), "Resistencia"),
				crearEstudiante("6780", "Acosta", "Sofía", "6780", 'F', LocalDate.of(1999, 9, 9), "Bariloche")
		);

		// Persistiendo los estudiantes
		for (Estudiante estudiante : estudiantes) {
			repEstudiante.persist(estudiante);
		}

	}

	private static void persistirInscripciones(CarreraRepository repCarerra,EstudianteRepository repEstudiante,InscripcionRepository repInscripcion, List<Estudiante> estudiantes, List<Carrera> carreras) {
		Random random = new Random();

		if (!(estudiantes.isEmpty() || carreras.isEmpty())) {
			for (int i = 0; i < 10; i++) { // Generamos 10 inscripciones
				// Obtiene un estudiante y una carrera aleatorios
				String idEstudiante = estudiantes.get(random.nextInt(estudiantes.size())).getDniEstudiante();
				Estudiante estudiante = repEstudiante.findById(idEstudiante);
				int idCarrera = carreras.get(random.nextInt(carreras.size())).getIdCarrera();
				Carrera carrera = repCarerra.findById(idCarrera);

				//Estudiante estudiante = estudiantes.get(random.nextInt(estudiantes.size()));
				//Carrera carrera = carreras.get(random.nextInt(carreras.size()));

				InscripcionId id = new InscripcionId(
						estudiante.getDniEstudiante(),
						carrera.getIdCarrera()
				);

				// Verifica si la inscripción ya existe
				if (repInscripcion.exist(id)) {
					continue; // Salta a la siguiente iteración
				}

				// Verifica que la carrera ya exista y obtén la fecha actual
				Date fechaInscripcion = new Date(2020); // Usa la fecha actual
				boolean graduado = random.nextBoolean(); // Valor aleatorio para graduado

				// Crea la inscripción
				Inscripcion inscripcion = crearInscripcion(id, estudiante, carrera, fechaInscripcion, graduado);
				System.out.println("carrera a insertar: "+inscripcion.getId().getIdCarrera());
				// Persistir la inscripción
				//repInscripcion.persist(inscripcion);
				repInscripcion.save(inscripcion);
				System.out.println("llegue 4");
			}
		}
	}

	private static void persistirCarreras(CarreraRepository repCarrera) {

		List<Carrera> carreras = List.of(
				crearCarrera(1, "Ingeniería Informática"),
				crearCarrera(2, "Medicina"),
				crearCarrera(3, "Arquitectura"),
				crearCarrera(4, "Derecho"),
				crearCarrera(5, "Contabilidad"),
				crearCarrera(6, "Ingeniería Civil"),
				crearCarrera(7, "Psicología"),
				crearCarrera(8, "Diseño Gráfico"),
				crearCarrera(9, "Administración de Empresas"),
				crearCarrera(10, "Ciencias de la Computación"),
				crearCarrera(11, "Biotecnología"),
				crearCarrera(12, "Economía"),
				crearCarrera(13, "Filosofía"),
				crearCarrera(14, "Sociología"),
				crearCarrera(15, "Matemáticas")
		);

		for (Carrera carrera : carreras) {
			repCarrera.persist(carrera);
		}
	}

    public static void main(String[] args) {
    	// Inicializacion de los repositorios
    	RepositoryFactory fac = JPARepositoryFactory.getInstance();
    	
    	CarreraRepository repCarrera = fac.getCarreraRepository();
    	InscripcionRepository repInscripcion = fac.getInscripcionRepository();
    	EstudianteRepository repEstudiante = fac.getEstudianteRepository();

		//DAR DE ALTA UN ESTUDIANTE
		persistirEstudiantes(repEstudiante);

		List<Estudiante> estudiantes = repEstudiante.findAll(); // Obtener la lista de estudiantes
		persistirCarreras(repCarrera);

		List<Carrera> carreras = repCarrera.findAll(); // Obtener la lista de carreras
		//MATRICULAR UN ESTUDIANTE EN UNA CARRERA


		System.out.println("estudiantes size: "+estudiantes.size());
		System.out.println("carreras size: "+carreras.size());
		persistirInscripciones(repCarrera,repEstudiante,repInscripcion, estudiantes, carreras);


		System.out.println("hola5");
		//Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
		//recuperarEstudiantesConOrdenamiento(repEstudiante);

		//Recuperar un estudiante, en base a su número de libreta universitaria.
		recuperEstudianteLU(repEstudiante);

		//Recuperar todos los estudiantes, en base a su género.
		recuperarEstudiantesGenero(repEstudiante);

		//Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
		recuperarCarrerasConInscriptosOrdXCant(repInscripcion);

		//Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
		recuperarEstudiantesDeCarreraXCiudad(repInscripcion);
	}

	private static void recuperarEstudiantesDeCarreraXCiudad(InscripcionRepository repInscripcion) {
	}

	private static void recuperarCarrerasConInscriptosOrdXCant(InscripcionRepository repInscripcion) {

	}

	private static void recuperarEstudiantesGenero(EstudianteRepository repEstudiante) {
		Condicion.CondicionGenero cond = new Condicion.CondicionGenero('M');
		List<Estudiante> estudiantes = repEstudiante.findWith(cond);
		if (estudiantes == null || estudiantes.isEmpty()) {
			System.out.println("No hay estudiantes para mostrar.");
		} else {
			imprimirEstudiantes(estudiantes);
		}
	}

	private static void recuperEstudianteLU(EstudianteRepository repEstudiante) {
		Condicion.CondicionLU cond = new Condicion.CondicionLU("2345");
		List<Estudiante> estudiantes = repEstudiante.findWith(cond);
		if (estudiantes == null || estudiantes.isEmpty()) {
			System.out.println("No hay estudiantes para mostrar.");
		} else {
			imprimirEstudiantes(estudiantes);
		}
	}

	private static void recuperarEstudiantesConOrdenamiento(EstudianteRepository repEstudiante) {
		Ordenamiento.OrdenamientoSimple o = new Ordenamiento.OrdenamientoSimple("apellido");
		List<Estudiante> estudiantes = repEstudiante.findWith(o);

		if (estudiantes == null || estudiantes.isEmpty()) {
			System.out.println("No hay estudiantes para mostrar.");
		} else {
			imprimirEstudiantes(estudiantes);
		}
	}


	public static void imprimirEstudiantes(List<Estudiante> estudiantes){
		System.out.println("Lista de Estudiantes:");
		for (Estudiante estudiante : estudiantes) {
			System.out.printf("DNI: %s, Nombre: %s %s, Libreta Universitaria: %s, Género: %c, Fecha de Nacimiento: %s, Ciudad: %s%n",
					estudiante.getDniEstudiante(),
					estudiante.getNombre(),
					estudiante.getApellido(),
					estudiante.getLibretaUniversitaria(),
					estudiante.getGenero(),
					estudiante.getFechaNacimiento(),
					estudiante.getCiudad());
		}
	}
}