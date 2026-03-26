package apirest.gympass.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas")
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RESERVA")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_EVENTO")
	private Evento evento;
	
	 @ManyToOne
	 @JoinColumn(name = "USERNAME")
	 private Usuario usuario;
	 
	 @Column(name = "PRECIO_VENTA")
	 private float precioVenta;
	 
	 private String observaciones;
	 private Integer cantidad;
	 
}
