package edu;

import java.time.LocalDate;

import entity.Estudiante;
import lombok.*;
import repository.*;
public class Main {
    public static void main(String[] args) {
    	// Inicializacion de los repositorios
    	RepositoryFactory fac = JPARepositoryFactory.getInstance();
    	
    	CarreraRepository repCarrera = fac.getCarreraRepository();
    	InscripcionRepository repInscripcion = fac.getInscripcionRepository();
    	EstudianteRepository repEstudiante = fac.getEstudianteRepository();
    	
    	Estudiante e = new Estudiante();
    	repEstudiante.persist(e);
    	
    }
}