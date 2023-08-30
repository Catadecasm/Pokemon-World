package com.example.pokemondemo.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.pokemondemo.domain.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    @Value("${PokemonDemo.seguridad.semilla}")
    private String SECRET_KEY;

    @Value("${PokemonDemo.seguridad.duracion}")
    private Long TOKEN_DURATION;

    Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(UserDetails userDetails, User user) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().getRoleName());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("userId", user.getId());
        claims.put("roleId", user.getRole().getId());
        claims.put("roleName", user.getRole().getRoleName());

        String token = Jwts.builder()
                .setId("softtekJWT")
                .setSubject(userDetails.getUsername())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_DURATION * 1000))
                .signWith(SignatureAlgorithm.HS256,
                        SECRET_KEY.getBytes()).compact();

        return token;
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Malformed token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported token");
        } catch (ExpiredJwtException e) {
            logger.error("Expired token");
        } catch (IllegalArgumentException e) {
            logger.error("Empty token");
        } catch (SignatureException e) {
            logger.error("Signature verification failed");
        }
        return false;
    }
}