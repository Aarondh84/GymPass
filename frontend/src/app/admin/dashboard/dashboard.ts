import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { Navbar } from '../../shared/navbar/navbar';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [Navbar, RouterLink],
  templateUrl: './dashboard.html',
})
export class Dashboard implements OnInit {
  totalEventos  = 0;
  totalReservas = 0;
  totalUsuarios = 0;
  totalActivos  = 0;
  cargando      = true;
  error         = false;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any>('/api/admin/stats').subscribe({
      next: (data) => {
        this.totalEventos  = data.totalEventos;
        this.totalReservas = data.totalReservas;
        this.totalUsuarios = data.totalUsuarios;
        this.totalActivos  = data.totalActivos;
        this.cargando      = false;
      },
      error: () => {
        this.cargando = false;
        this.error    = true;
      }
    });
  }
}