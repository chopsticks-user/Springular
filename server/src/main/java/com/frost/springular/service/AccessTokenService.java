package com.frost.springular.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.frost.springular.response.AccessTokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AccessTokenService {
  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public AccessTokenResponse generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public AccessTokenResponse generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiration);

    return new AccessTokenResponse(
        Jwts.builder()
            .claims()
            .add(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .and()
            .signWith(getLoginKey(), Jwts.SIG.HS256)
            .compact(),
        expirationDate);
  }

  public long getExpirationTime() {
    return jwtExpiration;
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    return (extractUsername(token).equals(userDetails.getUsername()))
        && !extractExpiration(token).before(new Date());
  }

  // Todo:
  public void revokeToken(String token) {
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getLoginKey()).build().parseSignedClaims(token).getPayload();
  }

  private SecretKey getLoginKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
