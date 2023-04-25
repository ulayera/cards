package com.logicea.cards.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  public String generateToken(String email) {
    final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    Date expiryDate = new Date(System.currentTimeMillis() + jwtExpiration);

    // Generate token
    return JWT.create()
        .withClaim("email", email)
        .withIssuedAt(new Date())
        .withExpiresAt(expiryDate)
        .withJWTId(UUID.randomUUID()
            .toString())
        .sign(algorithm);
  }

  public DecodedJWT verifyJWT(String jwtToken) {
    final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    final JWTVerifier verifier = JWT.require(algorithm)
        .build();
    try {
      return verifier.verify(jwtToken);
    } catch (JWTVerificationException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public boolean isJWTExpired(DecodedJWT decodedJWT) {
    Date expiresAt = decodedJWT.getExpiresAt();
    return expiresAt.getTime() < System.currentTimeMillis();
  }

}
