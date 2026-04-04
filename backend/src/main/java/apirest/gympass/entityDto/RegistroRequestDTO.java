package apirest.gympass.entityDto;

import lombok.Data;

// Datos que manda el frontend al registrarse
@Data
public class RegistroRequestDTO {
    private String username;
    private String password;
    private String email;
    private String nombre;
    private String apellidos;
}
