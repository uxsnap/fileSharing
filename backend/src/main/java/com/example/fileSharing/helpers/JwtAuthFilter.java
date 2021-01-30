package com.example.fileSharing.helpers;

import com.example.fileSharing.dto.RegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;

@AllArgsConstructor
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws AuthenticationException {
    try {
      RegisterDto userAndPassAuthDto = new ObjectMapper().readValue(
        request.getInputStream(),
        RegisterDto.class
      );

      Authentication authentication = new UsernamePasswordAuthenticationToken(
        userAndPassAuthDto.getUserName(),
        userAndPassAuthDto.getPassword()
      );

      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain,
    Authentication authResult
  ) {
    String token = Jwts.builder()
      .setSubject(authResult.getName())
      .claim("authorities", authResult.getAuthorities())
      .setIssuedAt(new Date())
      .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
      .signWith(Keys.hmacShaKeyFor(ConstantClass.SECRET_KEY.getBytes()))
      .compact();
    response.addHeader("Authorization", "Bearer " + token);
  }
}
