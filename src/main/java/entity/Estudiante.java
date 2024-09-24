package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private Date fechaNacimiento;

    @Column(nullable = false)
    private String ciudad;

    @OneToMany(mappedBy = "estudiante")
    private List<Inscripcion> inscripciones;

}
