package apirest.gympass.entityDto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

// Lo que el backend devuelve al frontend cuando pide usuarios
// No incluye la contraseña por seguridad
@Data
@AllArgsConstructor
public class UsuarioDTO {
    private String       username;
    private String       nombre;
    private String       apellidos;
    private String       email;
    private Integer      enabled;
    // Solo los nombres de los perfiles, no el objeto completo
    private List<String> perfiles;
}
