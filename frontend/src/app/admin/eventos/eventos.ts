import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-eventos',
  standalone: true,
  imports: [Navbar, ReactiveFormsModule],
  templateUrl: './eventos.html',
})
export class Eventos implements OnInit {
  eventos:      Evento[] = [];
  cargando      = true;
  modoForm      = false;
  editando      = false;
  mensajeOk     = '';
  mensajeError  = '';
  idEditando    = 0;

  form: FormGroup;

  constructor(private eventoService: EventoService, private http: HttpClient, private fb: FormBuilder) {
    this.form = this.fb.group({
      nombre:           ['', Validators.required],
      descripcion:      [''],
      fechaInicio:      ['', Validators.required],
      duracion:         [60, [Validators.required, Validators.min(1)]],
      direccion:        [''],
      aforoMaximo:      [20, [Validators.required, Validators.min(1)]],
      minimoAsistencia: [5,  [Validators.required, Validators.min(1)]],
      precio:           [10, [Validators.required, Validators.min(0)]],
      destacado:        ['N'],
      idTipo:           [1]
    });
  }

  get nombre()           { return this.form.get('nombre')!; }
  get fechaInicio()      { return this.form.get('fechaInicio')!; }
  get duracion()         { return this.form.get('duracion')!; }
  get aforoMaximo()      { return this.form.get('aforoMaximo')!; }
  get minimoAsistencia() { return this.form.get('minimoAsistencia')!; }
  get precio()           { return this.form.get('precio')!; }

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

  nuevo() {
    this.form.reset({
      duracion: 60, aforoMaximo: 20,
      minimoAsistencia: 5, precio: 10,
      destacado: 'N', idTipo: 1
    });
    this.editando     = false;
    this.modoForm     = true;
    this.mensajeOk    = '';
    this.mensajeError = '';
  }

  editar(evento: Evento) {
    this.form.setValue({
      nombre:           evento.nombre,
      descripcion:      evento.descripcion,
      fechaInicio:      evento.fechaInicio,
      duracion:         evento.duracion,
      direccion:        evento.direccion,
      aforoMaximo:      evento.aforoMaximo,
      minimoAsistencia: evento.minimoAsistencia,
      precio:           evento.precio,
      destacado:        evento.destacado,
      idTipo:           evento.idTipo
    });
    this.idEditando   = evento.idEvento;
    this.editando     = true;
    this.modoForm     = true;
    this.mensajeOk    = '';
    this.mensajeError = '';
  }

  cancelarForm() {
    this.modoForm = false;
    this.form.reset();
  }

  guardar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    if (this.editando) {
      this.eventoService.editar(this.idEditando, this.form.value).subscribe({
        next: () => {
          this.mensajeOk = 'Clase actualizada correctamente.';
          this.modoForm  = false;
          this.cargar();
        },
        error: () => this.mensajeError = 'Error al actualizar la clase.'
      });
    } else {
      this.eventoService.crear(this.form.value).subscribe({
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