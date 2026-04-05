package apirest.gympass.service;

import java.util.List;
import apirest.gympass.entityDto.UsuarioDTO;

// Esta interfaz define el contrato — que operaciones existen para Usuario
public interface UsuarioService {

    // Devuelve todos los usuarios sin exponer la contraseña
    List<UsuarioDTO> findAll();

    // Cambia el estado activo/inactivo del usuario y devuelve el nuevo estado
    boolean userEnabled(String username);

    // Elimina un usuario por su username
    void deleteByUsername(String username);
}