package apirest.gympass.entity;

import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "eventos")
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Evento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EVENTO")
	private Long id;
	
	private String nombre;
	private String descripcion;
	
	@Column(name = "FECHA_INICIO")
	private LocalDate fechaInicio;
	
	private Integer duracion;
	private String direccion;
	
	@Enumerated(EnumType.STRING)
	private EstadoEvento estado;
	
	private String destacado;
	
	@Column(name = "AFORO_MAXIMO")
	private Integer aforoMaximo;
	
	@Column(name = "MINIMO_ASISTENCIA")
	private Integer minimoAsistencia;
	
	private BigDecimal precio;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO")
	private Tipo tipo;
}
