package com.cognizant.insurance.security;


import java.nio.charset.StandardCharsets;
//import java.nio.charset.StandardCharsets;
//
//
//import java.util.Date;
//import java.util.List;
//import java.util.function.Function;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
// 
//import io.jsonwebtoken.Claims;
//
//import io.jsonwebtoken.Jwts;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import io.jsonwebtoken.security.Keys;
// 
//@Component
// 
//public class JWTService {
// 
//	private static final String SECRET_KEY = "01234567890123456789012345678901"; // min 32 bytes
//
////	public String generateToken(String username, String role) {
////
////	    return Jwts.builder()
////
////	        .setSubject(username)
////
////	        .claim("role", role)
////
////	        .setIssuedAt(new Date())
////
////	        .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 60 * 10))
////
////	        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), 
////
////	        		SignatureAlgorithm.HS256)
////
////	        .compact();
////
////	}     
//
//    public String extractRole(String token) {
// 
//        Claims claims = Jwts.parserBuilder()
// 
//            .setSigningKey(SECRET_KEY.getBytes())
// 
//            .build()
// 
//            .parseClaimsJws(token)
// 
//            .getBody();
// 
//        return claims.get("role", String.class);
// 
//    }
// 
//     
//
//    public String extractUsername(String token) {
// 
//        return Jwts.parserBuilder()
// 
//            .setSigningKey(SECRET_KEY.getBytes())
// 
//            .build()
// 
//            .parseClaimsJws(token)
// 
//            .getBody()
// 
//            .getSubject();
// 
//    }
//
//    public List<String> extractRoles(String token) {
// 
//        Claims claims = Jwts.parserBuilder()
// 
//            .setSigningKey(SECRET_KEY.getBytes())
// 
//            .build()
// 
//            .parseClaimsJws(token)
// 
//            .getBody();
// 
//        return (List<String>) claims.get("roles");
// 
//    }
//
//
//
//    public boolean validateToken(String token) {
// 
//        try {
// 
//            Jwts.parserBuilder()
// 
//                .setSigningKey(SECRET_KEY.getBytes())
// 
//                .build()
// 
//                .parseClaimsJws(token);
// 
//            return true;
// 
//        } catch (Exception e) {
// 
//            return false;
// 
//        }
// 
//    }
//
//
//
//
//
// 
//}
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {


	private static final String SECRET_KEY =  "01234567890123456789012345678901";

//    public JWTService() {
//
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public String generateToken(Users user) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("id",user.getUserId());
//        return Jwts.builder()
//                .claims()
//                .add(claims)
//                .subject(user.getEmail())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30*1000))
//                .and()
//                .signWith(getKey())
//                .compact();
//
//    }

    private SecretKey getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
 
 