package com.cognizant.insurance.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(customizer -> customizer.disable()).cors().and()
//                .authorizeHttpRequests(request -> request
//                		.requestMatchers("/validate-login", "/register").permitAll()
//                		.requestMatchers("customers/getAllPolicies").hasAnyRole("AGENT","CUSTOMER")
//                        .requestMatchers( "/agents/update/{agentID}","/agents/{agentId}","/customers/add","/customers/getAll","/agents/getAll","/agents/add","/agents/{agentId}/addpolicy","/agents/claims/status/{status}","/agents/claims/user/{customerID}","/agents/{claimId}/updateStatus","/agents/{agentId}/policies").hasRole("AGENT")
//                        .requestMatchers( "/customers/{customerID}","/customer/all","/customers/update/{customerID}", "/customers/{userId}/applyPolicy/{policyId}", "/customers/{userId}/fileClaim","customers/claims/user/{customerID}").hasRole("CUSTOMER")
//                        .anyRequest().permitAll())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//
//}
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
 
@Configuration
 
@EnableMethodSecurity
 
public class SecurityConfig {
 
    @Autowired
 
    private JwtFilter jwtAuthFilter;
 
    @Bean
 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 
        http
        .cors(cors -> cors.configurationSource(corsConfigurationSource())).
        csrf(AbstractHttpConfigurer::disable)//cross-site-requeust-forgery
 
            .sessionManagement(session -> session.sessionCreationPolicy
            		(SessionCreationPolicy.STATELESS))
 
            .authorizeHttpRequests(auth -> auth
 
                .requestMatchers
                ("/api/auth/**").permitAll()
 
               // .requestMatchers("/api/user/**").hasRole("user")
                .requestMatchers("/register","/users/**","/agents/**","/customers/**","/customers/{userId}/applyPolicy/{policyId}","/customers/{userId}/fileClaim").permitAll()
 
              //  .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
 
                .anyRequest().authenticated()
 
            )
 
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
 
    }
 
    @Bean
 
	public PasswordEncoder passwordEncoder() {
 
		return new BCryptPasswordEncoder();
 
	}
    @Bean
 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
 
		return config.getAuthenticationManager();
 
	}
 
        
        // CORS Configuration inside SecurityConfig
 
        @Bean
 
        public UrlBasedCorsConfigurationSource corsConfigurationSource() {
 
            CorsConfiguration config = new CorsConfiguration();
 
            config.setAllowedOrigins(List.of("http://localhost:3000")); // Allow frontend origin
 
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH")); // Allow necessary methods
 
            config.setAllowedHeaders(List.of("*")); // Allow all headers
 
            config.setAllowCredentials(true); // Enable credentials
     
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 
            source.registerCorsConfiguration("/**", config);
 
            return source;
 
        }
     
        @Bean
 
        public CorsFilter corsFilter() {
 
            return new CorsFilter(corsConfigurationSource());
 
        }
 
    }
 
 
