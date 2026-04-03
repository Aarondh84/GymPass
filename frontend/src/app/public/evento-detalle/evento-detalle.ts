import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-evento-detalle-publico',
  standalone: true,
  imports: [Navbar, RouterLink],
  templateUrl: './evento-detalle.html',
})
export class EventoDetalle implements OnInit {
  evento:   Evento | null = null;
  cargando  = true;

  constructor(
    private route:         ActivatedRoute,
    private eventoService: EventoService,
    private auth:          AuthService,
    private router:        Router
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventoService.getById(id).subscribe({
      next: (data) => {
        this.evento   = data;
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
  }

  intentarReservar() {
    if (this.auth.isLoggedIn()) {
      this.router.navigate(['/clientes/detalle', this.evento!.idEvento]);
    } else {
      this.router.navigate(['/login']);
    }
  }
}