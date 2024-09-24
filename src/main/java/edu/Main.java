package edu;

import entity.Estudiante;
import repository.EstudianteRepositoryImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Project");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Date ahora = new Date(2024, 9, 23);
        Estudiante e = new Estudiante("1","Pepe","Ramirez","unc_20932",'M',ahora,"Tandil",null);
        EstudianteRepositoryImpl epi = new EstudianteRepositoryImpl(em);
        epi.darAltaEstudiante(e);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}