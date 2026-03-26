package apirest.gympass.entityDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long idReserva;
    private Long idEvento;
    private String username;
    private float precioVenta;
    private String observaciones;
    private Integer cantidad;
    private String nombreEvento;
    private LocalDate fechaInicio;
}