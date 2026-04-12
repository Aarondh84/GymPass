import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';
import { EventoCard } from '../../shared/evento-card/evento-card';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Navbar, EventoCard, RouterLink],
  templateUrl: './home.html',
})
export class Home implements OnInit {
  eventos:   Evento[] = [];
  cargando   = true;
  error      = false;

  constructor(
    private eventoService: EventoService,
    public  auth:          AuthService
  ) {}

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