package apirest.gympass.service;

import java.util.List;
import apirest.gympass.entity.Usuario;

// Esta interfaz define el "contrato" — qué operaciones existen para Usuario
// La clase UsuarioServiceImpl será la que implemente estas operaciones
public interface UsuarioService {

    // Devuelve todos los usuarios de la base de datos
    List<Usuario> findAll();

    // Cambia el estado activo/inactivo del usuario y devuelve el nuevo estado
    boolean userEnabled(String username);

    // Elimina un usuario por su username
    void deleteByUsername(String username);
}