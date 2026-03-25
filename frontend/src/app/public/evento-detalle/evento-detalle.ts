import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-evento-detalle',
  standalone: true,
  imports: [Navbar, ReactiveFormsModule, RouterLink],
  templateUrl: './evento-detalle.html',
})
export class EventoDetalle implements OnInit {
  evento:      Evento | null = null;
  plazasLibres = 0;
  cargando     = true;
  reservando   = false;
  mensajeOk    = '';
  mensajeError = '';

  form: FormGroup;

  constructor(
    private route:         ActivatedRoute,
    private eventoService: EventoService,
    private auth:          AuthService,
    private http:          HttpClient,
    private fb:            FormBuilder
  ) {
    this.form = this.fb.group({
      cantidad: [1, [Validators.required, Validators.min(1), Validators.max(10)]]
    });
  }

  get cantidad() { return this.form.get('cantidad')!; }

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
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const cantidad = this.cantidad.value;

    if (cantidad > this.plazasLibres) {
      this.mensajeError = `Solo quedan ${this.plazasLibres} plazas disponibles.`;
      return;
    }

    this.reservando   = true;
    this.mensajeError = '';

    const reserva = {
      idEvento:    this.evento!.idEvento,
      username:    this.auth.currentUser()!.username,
      cantidad:    cantidad,
      precioVenta: this.evento!.precio
    };

    this.http.post('http://localhost:8080/api/reservas', reserva).subscribe({
      next: () => {
        this.mensajeOk  = `Reserva confirmada para ${cantidad} persona(s).`;
        this.reservando = false;
      },
      error: (err) => {
        if (err.status === 409) {
          this.mensajeError = 'Ya tienes una reserva con el máximo de entradas para esta clase.';
        } else {
          this.mensajeError = err.error?.mensaje ?? 'Error al realizar la reserva.';
        }
        this.reservando = false;
      }
    });
  }
}