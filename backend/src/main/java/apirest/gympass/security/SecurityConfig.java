package apirest.gympass.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

// Clase principal de configuracion de seguridad
// Define que rutas son publicas, cuales privadas,
// como se cifran las contraseñas y configura CORS para el frontend
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Define las reglas de seguridad para cada peticion HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            // Desactivamos CSRF porque usamos JWT y no sesiones
            .csrf(csrf -> csrf.disable())

            // Configuramos CORS para permitir peticiones desde Angular
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Sin sesion HTTP — cada peticion se autentica con el token
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            	    .requestMatchers("/api/auth/**").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/api/eventos/destacados").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/api/eventos/activos").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/api/tipos/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/reservas/plazas/**").permitAll()
            	    .requestMatchers("/api/reservas/**").authenticated()
            	    .requestMatchers("/api/usuarios/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.POST, "/api/eventos/**").authenticated()
            	    //.requestMatchers(HttpMethod.POST, "/api/eventos/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.PUT, "/api/eventos/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.POST, "/api/tipos/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.PUT, "/api/tipos/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.DELETE, "/api/tipos/**").hasAuthority("ROLE_ADMON")
            	    .requestMatchers(HttpMethod.GET, "/api/admin/stats").hasAuthority("ROLE_ADMON")
            	    .anyRequest().authenticated()
            	)

            // Añadimos nuestro filtro JWT antes del filtro de Spring Security
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // El AuthenticationManager es el que valida usuario y contraseña en el login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // BCrypt es el algoritmo que usamos para cifrar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuracion CORS — permite que Angular en puerto 4200 llame a esta API
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:4200",
            "https://gympass.aarondh.com"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}