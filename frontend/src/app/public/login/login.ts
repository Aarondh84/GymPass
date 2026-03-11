import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { LoginRequest } from '../../core/models/usuario.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
})
export class Login {
  credentials: LoginRequest = { username: '', password: '' };
  errorMsg = '';
  cargando = false;

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    if (!this.credentials.username || !this.credentials.password) {
      this.errorMsg = 'Por favor rellena todos los campos.';
      return;
    }

    this.cargando = true;
    this.errorMsg = '';

    this.auth.login(this.credentials).subscribe({
      next: (response) => {
        this.auth.saveSession(response);

        // Redirigir según el rol
        if (this.auth.isAdmin()) {
          this.router.navigate(['/admin/dashboard']);
        } else {
          this.router.navigate(['/clientes/activos']);
        }
      },
      error: () => {
        this.errorMsg = 'Usuario o contraseña incorrectos.';
        this.cargando = false;
      }
    });
  }
}