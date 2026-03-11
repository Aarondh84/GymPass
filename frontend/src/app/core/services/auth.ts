import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginRequest, LoginResponse } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private apiUrl = 'http://localhost:8080/api';
  currentUser = signal<LoginResponse | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    this.loadUserFromStorage();  // recupera sesión al arrancar
  }

  login(credentials: LoginRequest) {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, credentials);
  }

  saveSession(response: LoginResponse) {
    localStorage.setItem('token', response.token);
    localStorage.setItem('user', JSON.stringify(response));
    this.currentUser.set(response); // Para cambiar el valor (cuando haces login)
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null { return localStorage.getItem('token'); }
  isLoggedIn(): boolean     { return this.currentUser() !== null; }
  hasRole(role: string)     { return this.currentUser()?.roles.includes(role) ?? false; }
  isAdmin(): boolean        { return this.hasRole('ROLE_ADMON'); }
  isCliente(): boolean      { return this.hasRole('ROLE_CLIENTE'); }

  private loadUserFromStorage() {
    const user = localStorage.getItem('user');
    if (user) this.currentUser.set(JSON.parse(user));
  }
}
