package apirest.gympass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import apirest.gympass.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}