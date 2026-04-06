package apirest.gympass.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Este filtro se ejecuta una vez por cada peticion HTTP que llega al backend
// Su mision es leer el token JWT de la cabecera, verificarlo
// y si es valido marcar al usuario como autenticado
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtSecurityService     jwt;
    private final UserDetailsService     userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Leemos la cabecera Authorization de la peticion
        String authHeader = request.getHeader("Authorization");

        // Si no hay cabecera o no empieza por "Bearer " dejamos pasar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Quitamos "Bearer " del principio para quedarnos solo con el token
        String token = authHeader.substring(7);

        try {
            String username = jwt.extractUsername(token);

            // Solo procesamos si hay username y el usuario no esta ya autenticado
            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                // Cargamos el usuario de la BD para obtener sus roles
                UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

                if (jwt.isTokenValid(token)) {

                    // Creamos el objeto de autenticacion con los datos del usuario
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );

                    authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Marcamos al usuario como autenticado para esta peticion
                    SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            System.out.println("Error procesando JWT: " + e.getMessage());
        }

        // Dejamos continuar la peticion
        chain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // No aplicar el filtro JWT en rutas publicas
        return path.startsWith("/api/auth/");
    }
}