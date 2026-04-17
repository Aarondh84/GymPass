package apirest.gympass.service;

import java.util.List;

import apirest.gympass.entityDto.ReservaDTO;


public interface ReservaService {
  
  List<ReservaDTO> findByUsername (String username);
  void cancelarReserva(Long id);
  ReservaDTO crearReserva(ReservaDTO reservaDTO);
  long countReservasPorEvento(int idEvento);
}