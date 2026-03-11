CREATE DATABASE IF NOT EXISTS db_reto;

USE db_reto;

CREATE TABLE tipos (
ID_TIPO INT NOT NULL AUTO_INCREMENT,
NOMBRE VARCHAR(45),
DESCRIPCION VARCHAR(200),
PRIMARY KEY (ID_TIPO)
);

CREATE TABLE eventos (
ID_EVENTO INT NOT NULL AUTO_INCREMENT,
NOMBRE VARCHAR(50),
DESCRIPCION VARCHAR(200),
FECHA_INICIO DATE,
DURACION INT,
DIRECCION VARCHAR(100),
ESTADO ENUM('ACTIVO', 'CANCELADO', 'TERMINADO'),
DESTACADO ENUM('S', 'N'),
AFORO_MAXIMO INT,
MINIMO_ASISTENCIA INT,
PRECIO DECIMAL(9,2),
ID_TIPO INT,
PRIMARY KEY (ID_EVENTO),
CONSTRAINT fk_eventos_tipos FOREIGN KEY (ID_TIPO) REFERENCES tipos(ID_TIPO)
);

CREATE TABLE usuarios (
USERNAME VARCHAR(45) NOT NULL,
PASSWORD VARCHAR(100),
EMAIL VARCHAR(100),
NOMBRE VARCHAR(30),
APELLIDOS VARCHAR(45),
DIRECCION VARCHAR(100),
ENABLED INT,
FECHA_REGISTRO DATE,
PRIMARY KEY (USERNAME)
);

CREATE TABLE reservas (
ID_RESERVA INT NOT NULL AUTO_INCREMENT,
ID_EVENTO INT NOT NULL,
USERNAME VARCHAR(45) NOT NULL,
PRECIO_VENTA DECIMAL(9,2),
OBSERVACIONES VARCHAR(200),
CANTIDAD INT,
PRIMARY KEY (ID_RESERVA),
CONSTRAINT fk_reservas_eventos FOREIGN KEY (ID_EVENTO) REFERENCES eventos(ID_EVENTO),
CONSTRAINT fk_reservas_usuarios FOREIGN KEY (USERNAME) REFERENCES usuarios(USERNAME)
);

CREATE TABLE perfiles (
ID_PERFIL INT NOT NULL AUTO_INCREMENT,
NOMBRE VARCHAR(45),
PRIMARY KEY (ID_PERFIL)
);

CREATE TABLE usuario_perfiles (
USERNAME VARCHAR(45) NOT NULL,
ID_PERFIL INT NOT NULL,
PRIMARY KEY (USERNAME, ID_PERFIL),
CONSTRAINT fk_up_usuarios FOREIGN KEY (USERNAME) REFERENCES usuarios(USERNAME),
CONSTRAINT fk_up_perfiles FOREIGN KEY (ID_PERFIL) REFERENCES perfiles(ID_PERFIL)
);



-- Tipos de clase
INSERT INTO tipos (NOMBRE, DESCRIPCION) VALUES
('Cardio',     'Clases de ejercicio cardiovascular'),
('Fuerza',     'Clases de musculacion y fuerza'),
('Flexibilidad','Clases de estiramiento y movilidad'),
('Grupal',     'Clases colectivas con instructor');

-- Eventos
INSERT INTO eventos (NOMBRE, DESCRIPCION, FECHA_INICIO, DURACION, DIRECCION, ESTADO, DESTACADO, AFORO_MAXIMO, MINIMO_ASISTENCIA, PRECIO, ID_TIPO) VALUES
('Zumba Matinal',    'Clase de baile latino para todos los niveles', '2026-04-01', 60,  'Sala A', 'ACTIVO', 'S', 20, 5,  12.00, 4),
('BodyPump',         'Entrenamiento con barras y discos',            '2026-04-02', 50,  'Sala B', 'ACTIVO', 'S', 15, 4,  15.00, 2),
('CrossTraining',    'Entrenamiento funcional de alta intensidad',   '2026-04-03', 45,  'Sala C', 'ACTIVO', 'N', 12, 3,  18.00, 2),
('Yoga Restaurativo','Sesion de relajacion y estiramiento profundo', '2026-04-04', 60,  'Sala D', 'ACTIVO', 'S', 18, 4,  10.00, 3),
('Spinning',         'Ciclismo indoor de alta intensidad',           '2026-04-05', 45,  'Sala E', 'ACTIVO', 'N', 20, 5,  14.00, 1),
('Pilates',          'Fortalecimiento del core y postura',           '2026-04-06', 55,  'Sala A', 'ACTIVO', 'N', 15, 4,  12.00, 3);

-- Perfiles
INSERT INTO perfiles (NOMBRE) VALUES
('ROLE_ADMON'),
('ROLE_CLIENTE');

-- Usuarios (passwords en BCrypt — valor real: 'admin123' y 'cliente123')
INSERT INTO usuarios (USERNAME, PASSWORD, EMAIL, NOMBRE, APELLIDOS, ENABLED, FECHA_REGISTRO) VALUES
('admin',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@gympass.com',    'Admin',   'GymPass', 1, '2026-01-01'),
('cliente1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'cliente@gympass.com', 'Carlos',  'Garcia',  1, '2026-01-15');

-- Asignar perfiles
INSERT INTO usuario_perfiles (USERNAME, ID_PERFIL) VALUES
('admin',    1),
('cliente1', 2);