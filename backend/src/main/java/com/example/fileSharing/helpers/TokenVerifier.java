package com.example.fileSharing.helpers;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TokenVerifier extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String tokenHeader = request.getHeader("Authorization");
    if (Strings.isNullOrEmpty(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = tokenHeader.replace("Bearer ", "");

    try {
      Jws<Claims> claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(ConstantClass.SECRET_KEY.getBytes()))
        .build()
        .parseClaimsJws(token);

      Claims body = claims.getBody();
      String username = body.getSubject();

      List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

      Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities
        .stream()
        .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
        .collect(Collectors.toSet());

      Authentication auth = new UsernamePasswordAuthenticationToken(
        username,
        null,
        simpleGrantedAuthorities
      );

      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (JwtException e) {
      throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
    }
    filterChain.doFilter(request, response);
  }
}
