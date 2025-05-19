package com.cognizant.insurance.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    ApplicationContext context;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            username = jwtService.extractUsername(token);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            if (jwtService.validateToken(token)) {
//            	List<String> roles=jwtService.extractRoles(token);
//            	List<GrantedAuthority> authorities = roles.stream()
//            		    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//            		    .collect(Collectors.toList());
//
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
//                authToken.setDetails(new WebAuthenticationDetailsSource()
//                        .buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
	@Autowired
  private JWTService jwtUtil;

	    @Autowired
	    private UserDetailsService userDetailsService;
	    


	    @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    FilterChain filterChain) throws ServletException, IOException {

	        // Skip JWT validation for public endpoints (like /api/auth/**)
	    	 String path = request.getRequestURI();
	 	    if (path.startsWith("/api/auth/")) {
	 	        filterChain.doFilter(request, response);
	 	        return;
	 	    }
	  
	 	    String authHeader = request.getHeader("Authorization");
	  
	 	   if (authHeader != null && authHeader.startsWith("Bearer ")) {
	 		    String token = authHeader.substring(7);
	 		    String username = jwtUtil.extractUserName(token);

	 		    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	 		        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	 		        if (jwtUtil.validateToken(token, userDetails)) {
	 		            // If you want to extract a single role
	 		            String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));
	 		            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

	 		            UsernamePasswordAuthenticationToken authToken =
	 		                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

	 		            SecurityContextHolder.getContext().setAuthentication(authToken);
	 		        }
	 		    }
	 		}

	 	    filterChain.doFilter(request, response);
	    }
	}
