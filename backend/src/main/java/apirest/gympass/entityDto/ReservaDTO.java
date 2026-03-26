package apirest.gympass.entityDto;

public class ReservaDTO {
	
	package apirest.gympass.entityDto;

	import java.math.BigDecimal;
	import java.time.LocalDate;

	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ReservaDTO {
	    private Long        idReserva;
	    private Long        idEvento;
	    private String      username;
	    private BigDecimal  precioVenta;
	    private String      observaciones;
	    private Integer     cantidad;
	    private String      nombreEvento;
	    private LocalDate   fechaInicio;
	}

}
