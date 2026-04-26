# GymPass — Plataforma de Reserva de Clases

Aplicación web SPA desarrollada con Angular 19 para la gestión y reserva de clases de gimnasio.
Proyecto transversal del ciclo DAW (Desarrollo Web en Entorno Cliente).

---

## Tecnologías

- Angular 19 (Standalone Components, Lazy Loading)
- Bootstrap 5
- TypeScript
- JWT para autenticación
- Spring Boot (backend)
- MySQL 8

---

## Requisitos previos

- Node.js 18 o superior
- Angular CLI 19
- Backend Spring Boot arrancado en ``
- MySQL con la base de datos `db_reto` creada

---

## Instalación y ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/Aarondh84/GymPass.git
cd GymPass/frontend
```

### 2. Instalar dependencias
```bash
npm install
```

### 3. Arrancar el frontend
```bash
ng serve
```

La aplicación estará disponible en `http://localhost:4200`

### 4. Base de datos

Ejecutar el script SQL incluido en la carpeta `database/`:
```sql
mysql -u root -p < database/bd_reto.sql
```

---

## Estructura del proyecto
```
GymPass/
├── frontend/
│   └── src/
│       └── app/
│           ├── admin/
│           │   ├── dashboard/
│           │   ├── eventos/
│           │   ├── tipos-evento/
│           │   └── usuarios/
│           ├── cliente/
│           │   ├── evento-detalle/
│           │   ├── eventos-lista/
│           │   └── mis-reservas/
│           ├── core/
│           │   ├── guards/
│           │   ├── interceptors/
│           │   ├── models/
│           │   └── services/
│           ├── public/
│           │   ├── home/
│           │   ├── login/
│           │   └── registro/
│           └── shared/
│               ├── evento-card/
│               └── navbar/
└── database/
    └── bd_reto.sql
```

---

## Tipos de usuario y acceso

| Rol | Acceso |
|---|---|
| Invitado | Home, listado de clases, login, registro |
| Cliente | Reservar clases, ver y cancelar sus reservas |
| Administrador | CRUD de clases, usuarios y tipos de clase |

---

## Rutas principales

### Públicas
| Ruta | Descripción |
|---|---|
| `/home` | Página principal con clases destacadas |
| `/login` | Inicio de sesión |
| `/registro` | Registro de nuevos usuarios |

### Cliente (requiere autenticación)
| Ruta | Descripción |
|---|---|
| `/clientes/activos` | Listado de clases activas |
| `/clientes/destacados` | Listado de clases destacadas |
| `/clientes/detalle/:id` | Detalle de clase con botón reservar |
| `/clientes/misReservas` | Reservas futuras del usuario |

### Administrador (requiere rol admin)
| Ruta | Descripción |
|---|---|
| `/admin/dashboard` | Panel con estadísticas |
| `/admin/eventos` | CRUD de clases |
| `/admin/tipos-evento` | CRUD de tipos de clase |
| `/admin/usuarios` | Gestión de usuarios |

---

## Características principales

- Autenticación con JWT y redirección automática por rol
- Guards de ruta para proteger zonas privadas
- Interceptor HTTP que añade el token en cada petición
- Lazy loading en todos los componentes
- Formularios con Reactive Forms y validaciones
- Filtro de clases por tipo
- Límite de 2 reservas por día por usuario
- Accesibilidad: aria-label, labels enlazados, roles ARIA

---

## Autores

Jesús Sánchez-Infante
Beatriz Pérez 
Aarón Díaz — 2º DAW
