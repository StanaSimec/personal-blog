package cz.simec.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers("/", "/article/**").permitAll()
                                    .anyRequest().authenticated();
                        })
                .formLogin((formLogin) -> formLogin.loginPage("/login").permitAll())
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }
}
