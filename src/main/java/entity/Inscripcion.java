package entity;




import embebido.InscripcionId;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

@Entity
@IdClass(InscripcionId.class) // Usamos una clase ID compuesta
public class Inscripcion {

    @Id
    @ManyToOne // Relación con Carrera
    @JoinColumn(name = "idcarrera", referencedColumnName = "idcarrera")
    private Carrera carrera;

    @Id
    @ManyToOne // Relación con Estudiante
    @JoinColumn(name = "dniestudiante", referencedColumnName = "dniestudiante")
    private Estudiante estudiante;

    // Otros campos que desees agregar a Inscripcion
    @Column(nullable = false)
    private LocalDate fechaInscripcion;
    
    // Otros campos que desees agregar a Inscripcion
    @Column(nullable = false)
    private boolean graduado;
}