package entity;


import embebido.InscripcionId;

import javax.persistence.*;
import java.util.Date;

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
