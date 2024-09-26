package embebido;


import lombok.*;


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