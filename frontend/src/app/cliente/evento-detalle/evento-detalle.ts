import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-evento-detalle',
  standalone: true,
  imports: [Navbar, RouterLink],
  templateUrl: './evento-detalle.html',
})
export class EventoDetalle implements OnInit {
  evento:       Evento | null = null;
  plazasLibres  = 0;
  cargando      = true;
  reservando    = false;
  mensajeOk     = '';
  mensajeError  = '';

  constructor(
    private route:         ActivatedRoute,
    private eventoService: EventoService,
    private auth:          AuthService,
    private http:          HttpClient
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventoService.getById(id).subscribe({
      next: (data) => {
        this.evento   = data;
        this.cargando = false;
        this.calcularPlazas();
      },
      error: () => this.cargando = false
    });
  }

  calcularPlazas() {
    if (!this.evento) return;

    this.http.get<number>(`${environment.apiUrl}/reservas/plazas/${this.evento.idEvento}`)
      .subscribe({
        next: (ocupadas) => {
          this.plazasLibres = this.evento!.aforoMaximo - ocupadas;
        },
        error: () => {
          this.plazasLibres = this.evento!.aforoMaximo;
        }
      });
  }

  reservar() {
    this.reservando   = true;
    this.mensajeError = '';
    this.mensajeOk    = '';
    const reserva = {
      idEvento:    this.evento!.idEvento,
      username:    this.auth.currentUser()!.username,
      precioVenta: this.evento!.precio,
      cantidad:    1
    };

    this.http.post(`${environment.apiUrl}/reservas`, reserva).subscribe({
      next: () => {
        this.mensajeOk  = 'Reserva confirmada. Nos vemos en clase.';
        this.reservando = false;
        this.calcularPlazas();
      },
      error: (err) => {
        this.reservando = false;
        if (err.error && err.error.mensaje) {
          this.mensajeError = err.error.mensaje;
        } else if (err.status === 409) {
          this.mensajeError = 'Ya tienes 2 reservas para este día. No puedes reservar más clases.';
        } else if (err.status === 400) {
          this.mensajeError = 'No hay plazas disponibles para esta clase.';
        } else {
          this.mensajeError = 'Error al realizar la reserva.';
        }

      }
    });
  }
}