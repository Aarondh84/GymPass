export interface Reserva {
  idReserva:     number;
  idEvento:      number;
  username:      string;
  precioVenta:   number;
  observaciones?: string;
  cantidad?:     number;
  nombreEvento?: string;
  fechaInicio?:  string;
}