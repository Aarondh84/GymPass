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
        .map(this::convertToDto)
        .toList();
  }

  @Override
  public void cancelarReserva(Long id) {
    reservaRepository.deleteById(id);
  }

  @Override
  @Transactional
  public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
    int totalReservas = reservaRepository.countByUsuario_Username(reservaDTO.getUsername());

    if (totalReservas >= 10) {
      throw new RuntimeException("Has alcanzado el limite máximo de 10 reservas");
    }

    if (reservaRepository.existsByEvento_IdEventoAndUsuario_Username(reservaDTO.getIdEvento().intValue(), reservaDTO.getUsername())) {
      throw new RuntimeException("Ya tienes una reserva para este evento");
    }
    Evento evento = eventoRepository.findById(reservaDTO.getIdEvento().intValue())
        .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

    Usuario usuario = usuarioRepository.findById(reservaDTO.getUsername())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
      
    long ocupadas = reservaRepository.countByEvento_IdEvento(evento.getIdEvento());
    if (ocupadas >= evento.getAforoMaximo()) {
      throw new RuntimeException("No hay plazas disponibles");
    }

    Reserva reserva = new Reserva();
    reserva.setEvento(evento);
    reserva.setUsuario(usuario);
    reserva.setPrecioVenta(reservaDTO.getPrecioVenta());
    reserva.setObservaciones(reservaDTO.getObservaciones());
    reserva.setCantidad(reservaDTO.getCantidad());

    Reserva reservaGuardada = reservaRepository.save(reserva);

    return convertToDto(reservaGuardada);
  }

  @Override
  public long countReservasPorEvento(int idEvento) {
    return reservaRepository.countByEvento_IdEvento(idEvento);
  }

  public ReservaDTO convertToDto(Reserva reserva) {
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
  @Override
  @Transactional
  public List<ReservaDTO> findAll() {
      return reservaRepository.findAll().stream()
              .map(this::convertToDto)
              .toList();
  }
}