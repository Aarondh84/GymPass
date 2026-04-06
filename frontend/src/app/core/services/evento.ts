import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Evento } from '../models/evento.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class EventoService {

  private apiUrl = '/api/eventos';

  constructor(private http: HttpClient) {}

  getActivos() {
    return this.http.get<Evento[]>(`${this.apiUrl}/activos`);
  }

  getDestacados() {
    return this.http.get<Evento[]>(`${this.apiUrl}/destacados`);
  }

  getCancelados() {
    return this.http.get<Evento[]>(`${this.apiUrl}/cancelados`);
  }

  getTerminados() {
    return this.http.get<Evento[]>(`${this.apiUrl}/terminados`);
  }

  getById(id: number) {
    return this.http.get<Evento>(`${this.apiUrl}/${id}`);
  }

  crear(evento: Evento) {
    return this.http.post<Evento>(this.apiUrl, evento);
  }

  editar(id: number, evento: Evento) {
    return this.http.put<Evento>(`${this.apiUrl}/${id}`, evento);
  }

  cancelar(id: number) {
    return this.http.patch(`${this.apiUrl}/cancelar/${id}`, {});
  }

  eliminar(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}