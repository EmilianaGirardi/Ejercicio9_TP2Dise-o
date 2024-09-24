package entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

//Lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

//Javax persistence
@Entity
public class Estudiante
{
    
	@Id
    private String dniEstudiante;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "libreta_universitaria", nullable = false)
    private String libretaUniversitaria;

    @Column(nullable = false)
    private char genero;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String ciudad;

    @OneToMany(mappedBy = "estudiante")
    private List<Inscripcion> inscripciones;

}
