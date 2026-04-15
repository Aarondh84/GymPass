package apirest.gympass.Dtos;

import lombok.Data;

@Data
public class EventoDto {
    private Integer idEvento;
    private String nombre;
    private String fechaInicio;
    private Float precio;
    private Integer aforoMaximo;
    
}
