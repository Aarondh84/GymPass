import { Component, OnInit } from '@angular/core';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { Navbar } from '../../shared/navbar/navbar';
import { EventoCard } from '../../shared/evento-card/evento-card';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Navbar, EventoCard],
  templateUrl: './home.html',
})
export class Home implements OnInit {
  eventos:  Evento[] = [];
  cargando  = true;
  error     = false;

  constructor(private eventoService: EventoService) {}

  ngOnInit() {
    this.eventoService.getDestacados().subscribe({
      next: (data) => {
        this.eventos  = data;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.error    = true;
      }
    });
  }
}