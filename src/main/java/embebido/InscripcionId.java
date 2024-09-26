package embebido;


import entity.Carrera;
import entity.Estudiante;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class InscripcionId implements Serializable {
    private Carrera carrera;
    private Estudiante estudiante;

}

