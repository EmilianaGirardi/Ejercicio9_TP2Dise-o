package embebido;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class InscripcionId implements Serializable {
    private String dniEstudiante;
    private int idCarrera;
}
