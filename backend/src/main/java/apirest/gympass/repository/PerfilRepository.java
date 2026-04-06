package apirest.gympass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import apirest.gympass.entity.Perfil;

// Repository para la tabla perfiles
// Necesitamos findByNombre para buscar el perfil ROLE_CLIENTE al registrar
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    // Spring genera el SQL automaticamente a partir del nombre del metodo:
    // SELECT * FROM perfiles WHERE nombre = ?
    Optional<Perfil> findByNombre(String nombre);
}