package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Carrera implements Serializable {
    @Id
    private int idcarrera;

    @Column(nullable = false)
    private String nombre;


    @OneToMany(mappedBy = "carrera")
    private List<Inscripcion> inscripciones;

}

