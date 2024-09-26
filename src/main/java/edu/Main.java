package edu;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import condiciones.*;
import dto.ReporteCarreraDTO;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;
//import manager.EntityManagerProvider;
import repository.*;


public class Main {

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
		persistirInscripciones(repInscripcion, estudiantes, carreras);

		System.out.println("b.3");
		//Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
		recuperarEstudiantesConOrdenamiento(repEstudiante);

		System.out.println("b.4");
		//Recuperar un estudiante, en base a su número de libreta universitaria.
		recuperEstudianteLU(repEstudiante);

		System.out.println("b.5");
		//Recuperar todos los estudiantes, en base a su género.
		recuperarEstudiantesGenero(repEstudiante);

		System.out.println("b.6");
		//Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
		recuperarCarrerasConInscriptosOrdXCant(repInscripcion);

		System.out.println("b.7");
		//Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
		recuperarEstudiantesDeCarreraXCiudad(repInscripcion);

		System.out.println("b.8");
		//Generar un reporte de las carreras, que para cada carrera incluya información de los inscriptos y
		// egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar los años de manera cronológica.
		recuperarCarrerasInscriptosYEgresados(repCarrera);
	}

	//------------------------------FUNCIONES DE TABLAS-------------------------------------------------------------

	public static Carrera crearCarrera(int id, String n){
		Carrera car = new Carrera();
		car.setIdcarrera(id);
		car.setNombre(n);
		return car;
	}

	public static Inscripcion crearInscripcion(Estudiante e, Carrera c, LocalDate f, boolean g){
		Inscripcion ins = new Inscripcion();
		ins.setEstudiante(e);
		ins.setCarrera(c);
		ins.setFechaInscripcion(f);
		ins.setGraduado(g);
		return ins;
	}

	public static void persistirEstudiantes(EstudianteRepository repEstudiante){
		List<Estudiante> estudiantes = List.of(
				new Estudiante("1234", "Perez", "Juan", "1", 'M',LocalDate.of(2000,9,10),"Tandil",null),
				new Estudiante("2345", "Gómez", "Ana", "2345", 'F', LocalDate.of(2001, 5, 20), "Buenos Aires",null),
				new Estudiante("3456", "López", "Carlos", "3456", 'M', LocalDate.of(1999, 10, 12), "Córdoba",null),
				new Estudiante("4567", "Martínez", "Lucía", "4567", 'F', LocalDate.of(2000, 3, 5), "Rosario",null),
				new Estudiante("5678", "Pérez", "Javier", "5678", 'M', LocalDate.of(1998, 7, 30), "Mendoza",null),
				new Estudiante("6789", "Sánchez", "Mariana", "6789", 'F', LocalDate.of(2002, 1, 18), "La Plata",null),
				new Estudiante("7890", "Ramírez", "Pablo", "7890", 'M', LocalDate.of(2001, 9, 15), "Mar del Plata",null),
				new Estudiante("8901", "Torres", "Julieta", "8901", 'F', LocalDate.of(1997, 4, 10), "Salta",null),
				new Estudiante("9012", "Fernández", "Santiago", "9012", 'M', LocalDate.of(2000, 12, 22), "Neuquén",null),
				new Estudiante("0123", "González", "Paula", "0123", 'F', LocalDate.of(1999, 11, 3), "San Miguel de Tucumán",null),
				new Estudiante("1235", "Rodríguez", "Matías", "1234", 'M', LocalDate.of(1998, 6, 7), "Santa Fe",null),
				new Estudiante("2346", "Romero", "Camila", "2346", 'F', LocalDate.of(2001, 8, 14), "Posadas",null),
				new Estudiante("3457", "Alvarez", "Diego", "3457", 'M', LocalDate.of(2002, 2, 25), "San Juan",null),
				new Estudiante("4568", "Méndez", "Valentina", "4568", 'F', LocalDate.of(1997, 10, 28), "Río Cuarto",null),
				new Estudiante("5679", "Ruiz", "Tomás", "5679", 'M', LocalDate.of(2001, 12, 11), "Resistencia",null),
				new Estudiante("6780", "Acosta", "Sofía", "6780", 'F', LocalDate.of(1999, 9, 9), "Bariloche",null)
		);

		// Persistiendo los estudiantes
		for (Estudiante estudiante : estudiantes) {
			repEstudiante.persist(estudiante);
		}

	}

	private static void persistirInscripciones(InscripcionRepository repInscripcion, List<Estudiante> estudiantes, List<Carrera> carreras) {
		Random random = new Random();

		if (!(estudiantes.isEmpty() || carreras.isEmpty())) {
			for (int i = 0; i < 10; i++) { // Generamos 10 inscripciones
				// Obtiene un estudiante y una carrera aleatorios
				Estudiante estudiante = estudiantes.get(random.nextInt(estudiantes.size()));
				Carrera carrera = carreras.get(random.nextInt(carreras.size()));


				// Verifica que la carrera ya exista y obtén la fecha actual
				LocalDate fechaInscripcion = LocalDate.now(); // Usa la fecha actual
				boolean graduado = random.nextBoolean(); // Valor aleatorio para graduado

				// Crea la inscripción

				Inscripcion inscripcion = crearInscripcion(estudiante, carrera, fechaInscripcion, graduado);

				// Persistir la inscripción
				repInscripcion.save(inscripcion);

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

//------------------------------FUNCIONES DE CONSULTAS-------------------------------------------------------------

	private static void recuperarCarrerasInscriptosYEgresados(CarreraRepository repCarrera) {
		List<ReporteCarreraDTO> reportes = repCarrera.obtenerListadoCarreras();
		if (reportes == null || reportes.isEmpty()) {
			System.out.println("No hay carreras para mostrar.");
		} else {
			System.out.println("Listado de Reportes de Carrera:");
			for (ReporteCarreraDTO reporte : reportes) {
				System.out.println("Nombre de Carrera: " + reporte.getNombreCarrera() +
						", Año de Inscripción: " + reporte.getAnioInscripcion() +
						", Inscriptos: " + reporte.getInscriptos() +
						", Egresados: " + reporte.getEgresados());
			}
		}
	}

	private static void recuperarEstudiantesDeCarreraXCiudad(InscripcionRepository repInscripcion) {
		int idCarrera = 3;
		String ciudad = "Buenos Aires";

		Ordenamiento.OrdenamientoVacio ord = new Ordenamiento.OrdenamientoVacio();
		Condicion.CondicionEstPorCarreraYCiudad cond = new Condicion.CondicionEstPorCarreraYCiudad(idCarrera,ciudad);
		List<Estudiante> estudiantes = repInscripcion.getEstudiantes(cond, ord);
		if (estudiantes == null || estudiantes.isEmpty()) {
			System.out.println("No hay estudiantes para mostrar.");
		} else {
			imprimirEstudiantes(estudiantes);
		}
	}

	private static void recuperarCarrerasConInscriptosOrdXCant(InscripcionRepository repInscripcion) {
		Condicion.CondicionVacia cond = new Condicion.CondicionVacia();
		Ordenamiento.OrdenamientoCantInscriptos ord = new Ordenamiento.OrdenamientoCantInscriptos();
		List<Carrera> carreras = repInscripcion.getCarreras(cond,ord);
		if (carreras == null || carreras.isEmpty()) {
			System.out.println("No hay carreras para mostrar.");
		} else {
			System.out.println("Listado de Carreras:");
			for (Carrera carrera : carreras) {
				System.out.println("ID: " + carrera.getIdcarrera() + ", Nombre: " + carrera.getNombre());
			}
		}
	}

	private static void recuperarEstudiantesGenero(EstudianteRepository repEstudiante) {
		Condicion.CondicionGenero cond = new Condicion.CondicionGenero('F');
		List<Estudiante> estudiantes = repEstudiante.findWith(cond);
		if (estudiantes == null || estudiantes.isEmpty()) {
			System.out.println("No hay estudiantes para mostrar.");
		} else {
			imprimirEstudiantes(estudiantes);
		}
	}

	private static void recuperEstudianteLU(EstudianteRepository repEstudiante) {
		Condicion.CondicionLU cond = new Condicion.CondicionLU("1");
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
					estudiante.getDniestudiante(),
					estudiante.getNombre(),
					estudiante.getApellido(),
					estudiante.getLibretaUniversitaria(),
					estudiante.getGenero(),
					estudiante.getFechaNacimiento(),
					estudiante.getCiudad());
		}
	}
}
