import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './registro.html',
})
export class Registro {
  usuario = {
    username: '', email: '', password: '',
    nombre: '', apellidos: '', direccion: ''
  };
  errorMsg = '';
  exitoso  = false;
  cargando = false;

  constructor(private http: HttpClient) {}

  registrar() {
    if (!this.usuario.username || !this.usuario.email ||
        !this.usuario.password || !this.usuario.nombre) {
      this.errorMsg = 'Rellena todos los campos obligatorios.';
      return;
    }

    this.cargando = true;
    this.errorMsg = '';

    this.http.post('http://localhost:8080/api/auth/registro', this.usuario)
      .subscribe({
        next: () => {
          this.exitoso  = true;
          this.cargando = false;
        },
        error: (err) => {
          this.errorMsg = err.error?.mensaje ?? 'Error al registrar. Inténtalo de nuevo.';
          this.cargando = false;
        }
      });
  }
}