export interface Evento {
  idEvento:         number;
  nombre:           string;
  descripcion:      string;
  fechaInicio:      string;
  duracion:         number;
  direccion:        string;
  estado:           'ACTIVO' | 'CANCELADO' | 'TERMINADO';
  destacado:        'S' | 'N';
  aforoMaximo:      number;
  minimoAsistencia: number;
  precio:           number;
  idTipo:           number;
  nombreTipo?:      string;
}