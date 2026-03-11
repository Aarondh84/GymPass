export interface Usuario {
  username:        string;
  email:           string;
  nombre:          string;
  apellidos:       string;
  direccion?:      string;
  enabled:         number;
  fechaRegistro?:  string;
  perfiles?:       string[];
}

// Lo que enviamos al backend para hacer login
export interface LoginRequest {
  username: string;
  password: string;
}

// Lo que nos devuelve el backend tras login exitoso
export interface LoginResponse {
  token:    string;
  username: string;
  roles:    string[];
}