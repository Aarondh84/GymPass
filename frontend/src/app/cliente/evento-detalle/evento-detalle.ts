import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-evento-detalle',
  standalone: true,
  imports: [Navbar, FormsModule, RouterLink],
  templateUrl: './evento-detalle.html',
})
export class EventoDetalle implements OnInit {
  evento:      Evento | null = null;
  plazasLibres = 0;
  cantidad     = 1;
  cargando     = true;
  reservando   = false;
  mensajeOk    = '';
  mensajeError = '';

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
    this.http.get<number>(`http://localhost:8080/api/reservas/plazas/${this.evento.idEvento}`)
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
    if (this.cantidad > this.plazasLibres) {
      this.mensajeError = `Solo quedan ${this.plazasLibres} plazas disponibles.`;
      return;
    }

    this.reservando   = true;
    this.mensajeError = '';

    const reserva = {
      idEvento:    this.evento!.idEvento,
      username:    this.auth.currentUser()!.username,
      cantidad:    this.cantidad,
      precioVenta: this.evento!.precio
    };

    this.http.post('http://localhost:8080/api/reservas', reserva).subscribe({
      next: () => {
        this.mensajeOk  = `Reserva confirmada para ${this.cantidad} persona(s).`;
        this.reservando = false;
      },
      error: (err) => {
        this.mensajeError = err.error?.mensaje ?? 'Error al realizar la reserva.';
        this.reservando   = false;
      }
    });
  }
}