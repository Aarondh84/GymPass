import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Reserva } from '../../core/models/reserva.model';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-mis-reservas',
  standalone: true,
  imports: [Navbar, RouterLink],
  templateUrl: './mis-reservas.html',
})
export class MisReservas implements OnInit {
  reservas: Reserva[] = [];
  cargando  = true;

  constructor(private http: HttpClient, private auth: AuthService) {}

  ngOnInit() {
    const username = this.auth.currentUser()!.username;
    this.http.get<Reserva[]>(`/api/reservas/usuario/${username}`)
      .subscribe({
        next: (data) => {
          const hoy = new Date();
          hoy.setHours(0, 0, 0, 0);
          this.reservas = data.filter(r => {
            if (!r.fechaInicio) return true;
            return new Date(r.fechaInicio) >= hoy;
          });
          this.cargando = false;
        },
        error: () => this.cargando = false
      });
  }

  cancelar(idReserva: number) {
    if (!confirm('¿Seguro que quieres cancelar esta reserva?')) return;

    this.http.delete(`/api/reservas/${idReserva}`)
      .subscribe({
        next: () => {
          this.reservas = this.reservas.filter(r => r.idReserva !== idReserva);
        },
        error: () => alert('Error al cancelar la reserva.')
      });
  }
}