package apirest.gympass.entityDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private Long idEvento;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private Integer duracion;
    private String direccion;
    private String estado;
    private String destacado;
    private Integer aforoMaximo;
    private Integer minimoAsistencia;
    private float precio;
    private Long idTipo;
    private String nombreTipo;
}
