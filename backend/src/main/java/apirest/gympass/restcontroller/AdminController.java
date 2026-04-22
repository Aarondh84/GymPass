package apirest.gympass.restcontroller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import apirest.gympass.entity.EstadoEvento;
import apirest.gympass.repository.EventoRepository;
import apirest.gympass.repository.ReservaRepository;
import apirest.gympass.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final EventoRepository  eventoRepository;
    private final ReservaRepository reservaRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalUsuarios = usuarioRepository.count();
        long totalEventos  = eventoRepository.count();
        long totalActivos  = eventoRepository.findByEstado(EstadoEvento.ACTIVO).size();
        long totalReservas = reservaRepository.count();

        return ResponseEntity.ok(Map.of(
            "totalEventos",  totalEventos,
            "totalActivos",  totalActivos,
            "totalReservas", totalReservas,
            "totalUsuarios", totalUsuarios
        ));
    }
}