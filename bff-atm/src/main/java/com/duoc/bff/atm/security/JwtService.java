package com.duoc.bff.atm.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import java.security.Key; import java.util.*;
public class JwtService {
  private static final String SECRET = "super-secret-atm-key-32-bytes-super-secret-atm-key";
  private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
  public String generate(Map<String,Object> claims){ 
    return Jwts.builder().setClaims(claims).setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60))
      .signWith(KEY, SignatureAlgorithm.HS256).compact();
  }
  public Jws<Claims> parse(String token){ return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token); }
}
