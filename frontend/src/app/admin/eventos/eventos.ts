import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [Navbar, FormsModule],
  templateUrl: './eventos.html',
})
export class Eventos implements OnInit {
  eventos:   Evento[] = [];
  cargando   = true;
  modoForm   = false;   // true = mostrar formulario
  editando   = false;   // true = editar, false = crear
  mensajeOk  = '';
  mensajeError = '';

  form: Partial<Evento> = this.formVacio();

  constructor(
    private eventoService: EventoService,
    private http: HttpClient
  ) {}

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.eventoService.getActivos().subscribe({
      next: (data) => {
        this.eventos  = data;
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
  }

  formVacio(): Partial<Evento> {
    return {
      nombre: '', descripcion: '', fechaInicio: '',
      duracion: 60, direccion: '', aforoMaximo: 20,
      minimoAsistencia: 5, precio: 10,
      estado: 'ACTIVO', destacado: 'N', idTipo: 1
    };
  }

  nuevo() {
    this.form      = this.formVacio();
    this.editando  = false;
    this.modoForm  = true;
    this.mensajeOk = '';
    this.mensajeError = '';
  }

  editar(evento: Evento) {
    this.form      = { ...evento };  // copia del objeto
    this.editando  = true;
    this.modoForm  = true;
    this.mensajeOk = '';
    this.mensajeError = '';
  }

  cancelarForm() {
    this.modoForm = false;
    this.form     = this.formVacio();
  }

  guardar() {
    if (!this.form.nombre || !this.form.fechaInicio) {
      this.mensajeError = 'Nombre y fecha son obligatorios.';
      return;
    }

    if (this.editando && this.form.idEvento) {
      this.eventoService.editar(this.form.idEvento, this.form as Evento).subscribe({
        next: () => {
          this.mensajeOk = 'Clase actualizada correctamente.';
          this.modoForm  = false;
          this.cargar();
        },
        error: () => this.mensajeError = 'Error al actualizar la clase.'
      });
    } else {
      this.eventoService.crear(this.form as Evento).subscribe({
        next: () => {
          this.mensajeOk = 'Clase creada correctamente.';
          this.modoForm  = false;
          this.cargar();
        },
        error: () => this.mensajeError = 'Error al crear la clase.'
      });
    }
  }

  cancelarEvento(id: number) {
    if (!confirm('¿Cancelar esta clase? Los socios serán informados.')) return;
    this.eventoService.cancelar(id).subscribe({
      next: () => this.cargar(),
      error: () => alert('Error al cancelar.')
    });
  }

  eliminar(id: number) {
    if (!confirm('¿Eliminar esta clase? Esta acción no se puede deshacer.')) return;
    this.eventoService.eliminar(id).subscribe({
      next: () => this.cargar(),
      error: () => alert('Error al eliminar.')
    });
  }
}