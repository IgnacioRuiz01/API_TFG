package com.vedruna.twitterapi2ev.jwt;

import com.vedruna.twitterapi2ev.persistence.model.User;
import com.vedruna.twitterapi2ev.persistence.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "d3gsF8+FaBL186QRY5FKJcSKaMp91gR+XwZ89wUXpVI=";

    @Autowired
    private UserRepository userRepository;

    public String getToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        // Agregar el ID del usuario como reclamación adicional
        Long userId = getUserIdFromToken(user.getUsername());
        claims.put("user_id", userId);
        return getToken(claims, user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String username) {
        // Buscar el ID del usuario basado en el nombre de usuario
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return user.getId();
        } else {
            // Manejar el caso cuando el usuario no se encuentra en la base de datos
            // En este ejemplo, simplemente devolvemos null
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return getExpiration(token).before(new java.util.Date());
    }
}