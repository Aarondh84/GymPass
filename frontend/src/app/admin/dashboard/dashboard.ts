import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../../shared/navbar/navbar';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [Navbar, RouterLink],
  templateUrl: './dashboard.html',
})
export class Dashboard implements OnInit {
  totalEventos    = 0;
  totalReservas   = 0;
  totalUsuarios   = 0;
  totalActivos    = 0;
  cargando        = true;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any>('http://localhost:8080/api/admin/stats').subscribe({
      next: (data) => {
        this.totalEventos  = data.totalEventos;
        this.totalReservas = data.totalReservas;
        this.totalUsuarios = data.totalUsuarios;
        this.totalActivos  = data.totalActivos;
        this.cargando      = false;
      },
      error: () => this.cargando = false
    });
  }
}