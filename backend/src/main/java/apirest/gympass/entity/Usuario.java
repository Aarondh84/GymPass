package apirest.gympass.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id
	private String username;
	
	private String password;
	private String email;
	private String nombre;
	private String apellidos;
	private String direccion;
	private Integer enabled;
	
	@Column(name = "FECHA_REGISTRO")
	private LocalDate fechaRegistro;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_perfiles", joinColumns = @JoinColumn(name = "USERNAME"), inverseJoinColumns = @JoinColumn(name = "ID_PERFIL"))
	private Set<Perfil> perfiles = new HashSet<>();
}
