package Embebido;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@AllArgsConstructor
@Embeddable
public class InscripcionId {
    private String dniEstudiante;
    private int idCarrera;
}
