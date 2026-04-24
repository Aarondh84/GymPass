package apirest.gympass.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apirest.gympass.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Devuelve todas las reservas de un usuario
    List<Reserva> findByUsuario_Username(String username);

    // Cuenta las reservas de un evento para calcular plazas disponibles
    long countByEvento_IdEvento(int idEvento);

    // Comprueba si un usuario ya tiene reserva en un evento concreto
    boolean existsByEvento_IdEventoAndUsuario_Username(int idEvento, String username);

    int countByUsuario_Username(String username);
}