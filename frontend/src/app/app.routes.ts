import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { adminGuard } from './core/guards/admin-guard';

export const routes: Routes = [

  // ── ZONA PÚBLICA ──────────────────────────────────────
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home',
    loadComponent: () => import('./public/home/home').then(m => m.Home) },
  { path: 'login',
    loadComponent: () => import('./public/login/login').then(m => m.Login) },
  { path: 'registro',
    loadComponent: () => import('./public/registro/registro').then(m => m.Registro) },
  { path: 'evento/:id',
    loadComponent: () => import('./public/evento-detalle/evento-detalle').then(m => m.EventoDetalle) },

  // ── ZONA CLIENTE ──────────────────────────────────────
  { path: 'clientes', canActivate: [authGuard], children: [
    { path: '', redirectTo: 'activos', pathMatch: 'full' },
    { path: 'activos',
      loadComponent: () => import('./cliente/eventos-lista/eventos-lista').then(m => m.EventosLista) },
    { path: 'destacados',
      loadComponent: () => import('./cliente/eventos-lista/eventos-lista').then(m => m.EventosLista) },
    { path: 'detalle/:id',
      loadComponent: () => import('./cliente/evento-detalle/evento-detalle').then(m => m.EventoDetalle) },
    { path: 'misReservas',
      loadComponent: () => import('./cliente/mis-reservas/mis-reservas').then(m => m.MisReservas) },
  ]},

  // ── ZONA ADMIN ────────────────────────────────────────
  { path: 'admin', canActivate: [adminGuard], children: [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    { path: 'dashboard',
      loadComponent: () => import('./admin/dashboard/dashboard').then(m => m.Dashboard) },
    { path: 'eventos',
      loadComponent: () => import('./admin/eventos/eventos').then(m => m.Eventos) },
    { path: 'usuarios',
      loadComponent: () => import('./admin/usuarios/usuarios').then(m => m.Usuarios) },
  ]},

  { path: '**', redirectTo: 'home' }
];