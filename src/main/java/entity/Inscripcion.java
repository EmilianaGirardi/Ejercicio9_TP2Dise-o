package entity;


import embebido.InscripcionId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Inscripcion {
    @EmbeddedId
    private InscripcionId id;
    @ManyToOne
    @MapsId("dniEstudiante")
    @JoinColumn(name = "dni_estudiante")
    private Estudiante estudiante;
    @ManyToOne
    @MapsId("idCarrera")
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @Column(nullable = false, name = "fecha_inscripcion")
    private Date fechaInscripcion;

    @Column(nullable = false)
    private boolean graduado;

}
