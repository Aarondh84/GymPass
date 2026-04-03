package apirest.gympass.security;


import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
/**
 * Servicio encargado de todo lo relacionado con el ciclo de vida de los tokens JWT
 * Aquí es donde generamos los tokens al hacer login y donde verificamos que no hayan sido
 * alterados por nadie cuando nos los envían en una petición
 */
@Service
public class JwtSecurityService {

    @Value("${jwt.secret}")
    private String Secret;

    @Value("${jwt.expiration}")
    private Long expirationMs;
    
    /**
     * Convierte la clave secreta (guardada en el archivo properties) en un objeto Key
     * que la librería Jwts pueda entender para cifrar y descifrar.
     */
    private Key signingKey() {
        // Si el secret está en Base64:
        byte[] keyBytes = Decoders.BASE64.decode(Secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * Crea un nuevo token JWT para un usuario.
     * Incluye los roles en los 'claims' (datos extra ocultos en el token)
     * para que no tengamos que ir a la BD a preguntar qué puede hacer este usuario.
     * @param username El email del usuario.
     * @param auths Los roles o permisos que tiene el usuario.
     * @return El token en formato String listo para el frontend.
     */
    public String generateToken(String username, Collection<? extends GrantedAuthority> auths) {
        Map<String, Object> claims = Map.of(
                "roles",
                auths.stream().map(GrantedAuthority::getAuthority).toList()
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Abre el token (lo parsea) y extrae el 'subject', que en nuestro caso es el email.
     * @param token El token recibido del frontend.
     * @return El email del usuario.
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    /**
     * Verifica si un token es real y sigue activo
     * Si el token fue modificado, expiró o la firma no coincide con nuestra clave,
     * este método nos dirá que no es válido.
     * @param token El token a comprobar.
     * @return true si es auténtico y está en fecha, false si no.
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.out.println("Firma JWT inválida");
            return false;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("Token JWT expirado");
            return false;
        } catch (Exception e) {
            System.out.println("Token JWT inválido");
            return false;
        }
    }
}