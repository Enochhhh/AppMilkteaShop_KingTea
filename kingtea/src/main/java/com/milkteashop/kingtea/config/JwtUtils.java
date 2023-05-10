package com.milkteashop.kingtea.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private final String signingKey = "kingteamilkteashopsecret";
	
	public String extractEmail(String token) {
		String[] subjectArr = extractSubject(token).split(", ");
		return subjectArr[2];
	}
	
	public String extractUserName(String token) {
		String[] subjectArr = extractSubject(token).split(", ");
		return subjectArr[1];
	}
	
	public String extractSubject(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
	}
	
	public String generateToken(JwtUserPrincipal jwtUserPrincipal) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, jwtUserPrincipal);
	}
	
	public String generateToken(JwtUserPrincipal jwtUserPrincipal, Map<String, Object> claims) {
		return createToken(claims, jwtUserPrincipal);
	}
	
	private String createToken(Map<String, Object> claims, JwtUserPrincipal jwtUserPrincipal) {
		return Jwts.builder().setClaims(claims)
				.setSubject(String.format("%s, %s, %s", 
						jwtUserPrincipal.getUser().getId(), 
						jwtUserPrincipal.getUser().getUserName(), 
						jwtUserPrincipal.getUser().getEmail()))
				.claim("authorities", jwtUserPrincipal.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
				.signWith(SignatureAlgorithm.HS256, signingKey).compact();			
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername())
				&& !isTokenExpired(token));
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
