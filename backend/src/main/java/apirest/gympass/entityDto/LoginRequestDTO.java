package apirest.gympass.entityDto;

import lombok.Data;

// Datos que manda el frontend al hacer login
@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
