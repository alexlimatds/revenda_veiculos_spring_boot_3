package net.revenda;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("dev")
@Configuration(proxyBeanMethods = false)
public class DevProfileSecurityConfiguration {
    
    /* Configurações de segurança para permitir acesso ao console do H2 */
    @Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher(PathRequest.toH2Console()); // restringe estas configurações de seguranças para a URL do console do H2
		http.authorizeHttpRequests( authorize -> authorize
            .requestMatchers(PathRequest.toH2Console()).permitAll() // autoriza acesso não-autenticado ao console do H2
        );
		http.csrf((csrf) -> csrf.disable()); // desabilita tokens CSRF
		http.headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin())); //configura o cabeçalho HTTp X-Frame-Options
		return http.build();
	}
}
