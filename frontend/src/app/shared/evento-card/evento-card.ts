import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Evento } from '../../core/models/evento.model';

@Component({
  selector: 'app-evento-card',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './evento-card.html',
})
export class EventoCard {
  @Input() evento!: Evento;
  @Input() mostrarReserva = false;
}