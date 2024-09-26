package edu;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import condiciones.Ordenamiento;
import embebido.InscripcionId;
import entity.Carrera;
import entity.Estudiante;
import entity.Inscripcion;
import manager.EntityManagerProvider;
import repository.*;


public class Main {

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

				InscripcionId id = new InscripcionId(carrera,estudiante);

				// Verifica si la inscripción ya existe
				if (repInscripcion.exist(id)) {
					System.out.println("La inscripción ya existe para el estudiante " + estudiante.getDniEstudiante() +
							" en la carrera " + carrera.getIdCarrera());
					continue; // Salta a la siguiente iteración
				}

				// Verifica que la carrera ya exista y obtén la fecha actual
				LocalDate fechaInscripcion = LocalDate.now(); // Usa la fecha actual
				boolean graduado = random.nextBoolean(); // Valor aleatorio para graduado

				// Crea la inscripción
				Inscripcion inscripcion = new Inscripcion(carrera,estudiante,fechaInscripcion,graduado);

				// Persistir la inscripción
				repInscripcion.persist(inscripcion);
			}
		}
	}

	private static void persistirCarreras(CarreraRepository repCarrera) {
		List<Carrera> carreras = List.of(
				new Carrera(1, "Ingeniería Informática"),
				new Carrera(2, "Medicina"),
				new Carrera(3, "Arquitectura"),
				new Carrera(4, "Derecho"),
				new Carrera(5, "Contabilidad"),
				new Carrera(6, "Ingeniería Civil"),
				new Carrera(7, "Psicología"),
				new Carrera(8, "Diseño Gráfico"),
				new Carrera(9, "Administración de Empresas"),
				new Carrera(10, "Ciencias de la Computación"),
				new Carrera(11, "Biotecnología"),
				new Carrera(12, "Economía"),
				new Carrera(13, "Filosofía"),
				new Carrera(14, "Sociología"),
				new Carrera(15, "Matemáticas")
		);

		for (Carrera carrera : carreras) {
			repCarrera.persist(carrera);
		}
	}

	// TODO: Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
	private static void recuperarEstudiantesConOrdenamiento(EstudianteRepository repEstudiante) {
		Ordenamiento.OrdenamientoSimple o = new Ordenamiento.OrdenamientoSimple("apellido");
		List<Estudiante> estudiantes = repEstudiante.findWith(o);

		if (estudiantes == null || estudiantes.isEmpty()) {
			System.out.println("No hay estudiantes para mostrar.");
		} else {
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

	
    public static void main(String[] args) {
    	// Inicializacion de los repositorios
    	RepositoryFactory fac = JPARepositoryFactory.getInstance();
    	
    	CarreraRepository repCarrera = fac.getCarreraRepository();
    	InscripcionRepository repInscripcion = fac.getInscripcionRepository();
    	EstudianteRepository repEstudiante = fac.getEstudianteRepository();

		// TODO: DAR DE ALTA UN ESTUDIANTE
		persistirEstudiantes(repEstudiante);

		List<Estudiante> estudiantes = repEstudiante.findAll(); // Obtener la lista de estudiantes
		persistirCarreras(repCarrera);

		List<Carrera> carreras = repCarrera.findAll(); // Obtener la lista de carreras
		// TODO: MATRICULAR UN ESTUDIANTE EN UNA CARRERA


		System.out.println("estudiantes size: "+estudiantes.size());
		System.out.println("carreras size: "+carreras.size());
		persistirInscripciones(repInscripcion, estudiantes, carreras);


		List<Inscripcion> inscripciones = repInscripcion.findAll(); // Obtener la lista de carreras
		// TODO: MATRICULAR UN ESTUDIANTE EN UNA CARRERA

		
    	recuperarEstudiantesConOrdenamiento(repEstudiante);
    	EntityManagerProvider.closeEntityManager();
	}
	
}