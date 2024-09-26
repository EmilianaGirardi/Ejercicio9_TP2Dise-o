package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class ReporteCarreraDTO {
    private String nombreCarrera;
    private int anioInscripcion;
    private long inscriptos;
    private long egresados;

    public ReporteCarreraDTO(String nombreCarrera, Date fechaInscripcion, long inscriptos, long egresados) {
        this.nombreCarrera = nombreCarrera;
        this.anioInscripcion = fechaInscripcion.getYear();
        this.inscriptos = inscriptos;
        this.egresados = egresados;
    }
}
