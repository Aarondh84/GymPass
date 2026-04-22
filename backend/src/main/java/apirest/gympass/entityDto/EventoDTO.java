package apirest.gympass.entityDto;

import java.time.LocalDate;

import apirest.gympass.entity.EstadoEvento;
import lombok.Data;

@Data
public class EventoDTO {
    private int idEvento;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio; 
    private int duracion; 
    private String direccion; 
    private String estado; 
    private String destacado; 
    private int aforoMaximo; 
    private int minimoAsistencia; 
    private double precio; 
    private int idTipo; 
}



