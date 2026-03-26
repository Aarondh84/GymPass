export interface Perfil {
  idPerfil: number;
  nombre:   string;
}

export interface Usuario {
  username:      string;
  email:         string;
  nombre:        string;
  apellidos:     string;
  direccion?:    string;
  enabled:       number;
  fechaRegistro?: string;
  perfiles:      Perfil[];
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