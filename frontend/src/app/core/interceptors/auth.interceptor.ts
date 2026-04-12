import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth   = inject(AuthService);
  const router = inject(Router);
  const token  = auth.getToken();

  // Si hay token lo añadimos a la cabecera de la peticion
  const authReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {

      // 401 — token expirado o invalido
      // Cerramos sesion y redirigimos al login automaticamente
      if (error.status === 401) {
        auth.logout();
        router.navigate(['/login']);
      }

      // Para cualquier otro error lo dejamos pasar
      // para que cada componente lo gestione como quiera
      return throwError(() => error);
    })
  );
};