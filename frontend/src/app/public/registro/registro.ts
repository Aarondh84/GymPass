import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [Navbar, ReactiveFormsModule, RouterLink],
  templateUrl: './registro.html',
})
export class Registro {
  form: FormGroup;
  cargando     = false;
  mensajeOk    = '';
  mensajeError = '';

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.form = this.fb.group({
      username:  ['', Validators.required],
      password:  ['', [Validators.required, Validators.minLength(6)]],
      email:     ['', [Validators.required, Validators.email]],
      nombre:    ['', Validators.required],
      apellidos: ['', Validators.required]
    });
  }

  get username()  { return this.form.get('username')!; }
  get password()  { return this.form.get('password')!; }
  get email()     { return this.form.get('email')!; }
  get nombre()    { return this.form.get('nombre')!; }
  get apellidos() { return this.form.get('apellidos')!; }

  registrar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.cargando    = true;
    this.mensajeError = '';

    this.http.post('/api/auth/registro', this.form.value).subscribe({
      next: () => {
        this.cargando  = false;
        this.mensajeOk = 'Cuenta creada correctamente. Ya puedes iniciar sesión.';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        this.cargando    = false;
        this.mensajeError = err.error?.mensaje ?? 'Error al crear la cuenta.';
      }
    });
  }
}