package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Carrera {
    @Id
    private int idCarrera;

    @Column(nullable = false)
    private String nombre;

}
