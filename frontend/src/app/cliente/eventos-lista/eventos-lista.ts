import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../../core/models/evento.model';
import { EventoService } from '../../core/services/evento';
import { Navbar } from '../../shared/navbar/navbar';
import { EventoCard } from '../../shared/evento-card/evento-card';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-eventos-lista',
  standalone: true,
  imports: [Navbar, EventoCard, FormsModule],
  templateUrl: './eventos-lista.html',
})
export class EventosLista implements OnInit {
  eventos:          Evento[] = [];
  eventosFiltrados: Evento[] = [];
  tipos:            any[]    = [];
  tipoSeleccionado  = '';
  filtro            = 'activos';
  cargando          = true;
  error             = false;

  constructor(
    private eventoService: EventoService,
    private route:         ActivatedRoute,
    private http:          HttpClient
  ) {}

  ngOnInit() {
    this.route.url.subscribe(url => {
      this.filtro = url[0]?.path ?? 'activos';
      this.cargarEventos();
    })
    this.cargarTipos();
  }

  cargarEventos() {
    this.cargando = true;
    this.error    = false;
    const peticion =
      this.filtro === 'destacados' ? this.eventoService.getDestacados() :
      this.filtro === 'cancelados' ? this.eventoService.getCancelados() :
      this.filtro === 'terminados' ? this.eventoService.getTerminados() :
      this.eventoService.getActivos();

    peticion.subscribe({
      next: (data) => {
        this.eventos = data;
        this.filtrarPorTipo();
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.error = true;
      }
    });
  }

  cargarTipos() {
    this.http.get<any[]>(`${environment.apiUrl}/tipos`).subscribe({
      next: (data) => this.tipos = data,
      error: (err) => console.error('Error cargando tipos', err)
    });
  }

  filtrarPorTipo() {
    if (!this.tipoSeleccionado) {
      this.eventosFiltrados = this.eventos;
    } else {
      const idBusqueda = Number(this.tipoSeleccionado);
      this.eventosFiltrados = this.eventos.filter( e => {
        const idActual = e.idTipo;
        return idActual === idBusqueda;
      });
    }
  }
}