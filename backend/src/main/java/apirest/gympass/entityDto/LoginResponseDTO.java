package apirest.gympass.entityDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

// Datos que devuelve el backend tras un login correcto
// El frontend guarda esto en localStorage
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String       token;
    private String       username;
    private List<String> roles;
}