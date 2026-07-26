package com.app.reunite.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private static final String SECRET_KEY = "";
    @Value("${application.security.jwt.expiration}")
    private static final long TOKEN_EXPIRATION = 0;
    @Value("${application.security.jwt.refresh.window}")
    private static final long REFRESH_WINDOW = 0;

    public String generateToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = Map.of("authorities",
                userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList());
        return generateToken(claims,userDetails.getUsername());
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode((SECRET_KEY)));
    }

    private Claims getAllClaims(String token){
        try {
            return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | io.jsonwebtoken.security.SignatureException | IllegalArgumentException e) {
            throw new RuntimeException("token JWT invalido");
        }
    }

    private <T> T getClaim(String token, Function<Claims,T> claimsMapper){
        Claims claims = getAllClaims(token);
        return claimsMapper.apply(claims);
    }

    public String getUsername(String token){
        return getClaim(token,Claims::getSubject);
    }
    public String getAuthorities(String token){
        return getClaim(token,c -> c.get("authorities", List.class)).toString();
    }
    public Date getExpirationDate(String token){
        return getClaim(token,Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return getExpirationDate(token).before(new Date());
    }
    public boolean canBeTokenRenewed(String token){
        return getExpirationDate(token).before(new Date(System.currentTimeMillis() + REFRESH_WINDOW));
    }
    public String renewToken(String token,UserDetails userDetails){
        if(!canBeTokenRenewed(token)){
            throw new RuntimeException("Token cannot be renewed");
        }
        return generateToken(userDetails);
    }

}
