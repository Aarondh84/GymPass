package apirest.gympass.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apirest.gympass.entity.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    
    List<Reserva> findByUsuario_Username(String username);

    long countByEvento_IdEvento(int idEvento);
}