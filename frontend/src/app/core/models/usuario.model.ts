export interface Usuario {
  username:      string;
  email:         string;
  nombre:        string;
  apellidos:     string;
  direccion?:    string;
  enabled:       number;
  fechaRegistro?: string;
  perfiles:      string[];
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token:    string;
  username: string;
  roles:    string[];
}