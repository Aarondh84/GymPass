import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../../shared/navbar/navbar';

interface TipoEvento {
  idTipo:      number;
  nombre:      string;
  descripcion: string;
}

@Component({
  selector: 'app-tipos-evento',
  standalone: true,
  imports: [Navbar, ReactiveFormsModule],
  templateUrl: './tipos-evento.html',
})
export class TiposEvento implements OnInit {
  tipos:        TipoEvento[] = [];
  cargando      = true;
  modoForm      = false;
  editando      = false;
  mensajeOk     = '';
  mensajeError  = '';
  idEditando    = 0;

  form: FormGroup;

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.form = this.fb.group({
      nombre:      ['', Validators.required],
      descripcion: ['', Validators.required]
    });
  }

  get nombre()      { return this.form.get('nombre')!; }
  get descripcion() { return this.form.get('descripcion')!; }

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.http.get<TipoEvento[]>('/api/tipos').subscribe({
      next: (data) => {
        this.tipos    = data;
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
  }

  nuevo() {
    this.form.reset();
    this.editando    = false;
    this.modoForm    = true;
    this.mensajeOk   = '';
    this.mensajeError = '';
  }

  editar(tipo: TipoEvento) {
    this.form.setValue({ nombre: tipo.nombre, descripcion: tipo.descripcion });
    this.idEditando  = tipo.idTipo;
    this.editando    = true;
    this.modoForm    = true;
    this.mensajeOk   = '';
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
      this.http.put(`/api/tipos/${this.idEditando}`, this.form.value)
        .subscribe({
          next: () => {
            this.mensajeOk = 'Tipo actualizado correctamente.';
            this.modoForm  = false;
            this.cargar();
          },
          error: () => this.mensajeError = 'Error al actualizar el tipo.'
        });
    } else {
      this.http.post('/api/tipos', this.form.value)
        .subscribe({
          next: () => {
            this.mensajeOk = 'Tipo creado correctamente.';
            this.modoForm  = false;
            this.cargar();
          },
          error: () => this.mensajeError = 'Error al crear el tipo.'
        });
    }
  }

  eliminar(idTipo: number) {
    if (!confirm('¿Eliminar este tipo? Las clases asociadas perderán su categoría.')) return;
    this.http.delete(`/api/tipos/${idTipo}`).subscribe({
      next: () => {
        this.tipos     = this.tipos.filter(t => t.idTipo !== idTipo);
        this.mensajeOk = 'Tipo eliminado.';
      },
      error: () => alert('Error al eliminar. Puede que haya clases asociadas a este tipo.')
    });
  }
}