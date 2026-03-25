import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { Navbar } from '../../shared/navbar/navbar';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [Navbar, ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
})
export class Login {
  form: FormGroup;
  cargando    = false;
  mensajeError = '';

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get username() { return this.form.get('username')!; }
  get password() { return this.form.get('password')!; }

  login() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.cargando    = true;
    this.mensajeError = '';

    this.auth.login(this.form.value).subscribe({
      next: () => {
        this.cargando = false;
        if (this.auth.isAdmin()) {
          this.router.navigate(['/admin/dashboard']);
        } else {
          this.router.navigate(['/clientes/activos']);
        }
      },
      error: () => {
        this.cargando    = false;
        this.mensajeError = 'Usuario o contraseña incorrectos.';
      }
    });
  }
}