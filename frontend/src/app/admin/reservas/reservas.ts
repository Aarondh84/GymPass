import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../../shared/navbar/navbar';

interface Reserva {
  idReserva: number;
  idEvento:  number;
  username:  string;
  precioVenta: number;
  cantidad:  number;
  nombreEvento: string;
  fechaInicio: string;
}

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [Navbar],
  templateUrl: './reservas.html',
})
export class Reservas implements OnInit {
  reservas: Reserva[] = [];
  cargando  = true;
  error     = false;

  constructor(private http: HttpClient) {}

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.error    = false;
    this.http.get<Reserva[]>('/api/reservas').subscribe({
      next: (data) => {
        this.reservas = data;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.error    = true;
      }
    });
  }

  cancelar(id: number) {
    if (!confirm('¿Cancelar esta reserva?')) return;
    this.http.delete(`/api/reservas/${id}`).subscribe({
      next: () => {
        this.reservas = this.reservas.filter(r => r.idReserva !== id);
      },
      error: () => alert('Error al cancelar la reserva.')
    });
  }
}