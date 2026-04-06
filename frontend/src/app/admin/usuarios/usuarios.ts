import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../../core/models/usuario.model';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [Navbar],
  templateUrl: './usuarios.html',
})
export class Usuarios implements OnInit {
  usuarios: Usuario[] = [];
  cargando  = true;
  mensajeOk = '';

  constructor(private http: HttpClient) {}

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.http.get<Usuario[]>('/api/usuarios').subscribe({
      next: (data) => {
        this.usuarios = data;
        this.cargando = false;
      },
      error: () => this.cargando = false
    });
  }

  toggleActivo(usuario: Usuario) {
    const nuevoEstado = usuario.enabled === 1 ? 0 : 1;
    this.http.patch(`/api/usuarios/${usuario.username}/estado`,
      { enabled: nuevoEstado }).subscribe({
        next: () => {
          usuario.enabled = nuevoEstado;
          this.mensajeOk  = `Usuario ${usuario.username} ${nuevoEstado === 1 ? 'activado' : 'desactivado'}.`;
        },
        error: () => alert('Error al cambiar el estado.')
      });
  }

  eliminar(username: string) {
    if (!confirm(`¿Eliminar el usuario ${username}?`)) return;
    this.http.delete(`/api/usuarios/${username}`).subscribe({
      next: () => {
        this.usuarios  = this.usuarios.filter(u => u.username !== username);
        this.mensajeOk = 'Usuario eliminado.';
      },
      error: () => alert('Error al eliminar.')
    });
  }
}