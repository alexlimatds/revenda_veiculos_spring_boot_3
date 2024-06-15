package net.revenda;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        String loginUrl = "/login";
        
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/styles.css").permitAll()
                .requestMatchers(loginUrl, "/logout").permitAll()         
                //.requestMatchers("/admin/**").hasRole("ADMIN")                             
                //.requestMatchers("/db/**").access(allOf(hasAuthority("db"), hasRole("ADMIN")))   
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage(loginUrl)
                .defaultSuccessUrl("/")
            );
	    
        return http.build();
    }
}
