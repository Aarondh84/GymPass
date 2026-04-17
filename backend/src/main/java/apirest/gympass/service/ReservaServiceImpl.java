package apirest.gympass.service;


import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import apirest.gympass.entity.Evento;
import apirest.gympass.entity.Usuario;
import apirest.gympass.entity.Reserva;
import apirest.gympass.entityDto.ReservaDTO;
import apirest.gympass.repository.ReservaRepository;
import apirest.gympass.repository.EventoRepository;
import apirest.gympass.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {
  private final ReservaRepository reservaRepository;
  private final EventoRepository eventoRepository;
  private final UsuarioRepository usuarioRepository;

  @Override
  public List<ReservaDTO> findByUsername(String username) {
    return reservaRepository.findByUsuario_Username(username).stream()
        .map(this::converToDto)
        .toList();
  }

  @Override
  public void cancelarReserva(Long id) {
    reservaRepository.deleteById(id);
  }

  @Override
  @Transactional
  public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
    Evento evento = eventoRepository.findById(reservaDTO.getIdEvento().intValue())
        .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

    Usuario usuario = usuarioRepository.findById(reservaDTO.getUsername())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
      
    if (evento.getAforoMaximo() <= 0) {
      throw new RuntimeException("No hay plazas disponibles");
    }

    Reserva reserva = new Reserva();
    reserva.setEvento(evento);
    reserva.setUsuario(usuario);
    reserva.setPrecioVenta(reservaDTO.getPrecioVenta());
    reserva.setObservaciones(reservaDTO.getObservaciones());
    reserva.setCantidad(reservaDTO.getCantidad());

    Reserva reservaGuardada = reservaRepository.save(reserva);

    return converToDto(reservaGuardada);
  }

  @Override
  public long countReservasPorEvento(int idEvento) {
    return reservaRepository.countByEvento_IdEvento(idEvento);
  }

  private ReservaDTO converToDto(Reserva reserva) {
    return new ReservaDTO(
        reserva.getId(),
        reserva.getEvento().getIdEvento().longValue(),
        reserva.getUsuario().getUsername(),
        reserva.getPrecioVenta(),
        reserva.getObservaciones(),
        reserva.getCantidad(),
        reserva.getEvento().getNombre(),
        reserva.getEvento().getFechaInicio()
    );
  }
}