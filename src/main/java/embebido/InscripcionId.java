package embebido;

import java.io.Serializable;

import entity.Carrera;
import entity.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InscripcionId implements Serializable {
    private Carrera carrera;
    private Estudiante estudiante;

}