package org.springproject.coffeeshopapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthSuccess authSuccess = new AuthSuccess();

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/", "register", "/signin", "/saveUser", "/menu", "/contact","/about", "/item").permitAll()
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/admin/**").authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/userLogin")
                        .defaultSuccessUrl("/")
                        .successHandler(authSuccess)
                        .permitAll()
                );
        return http.build();

    }
    @Bean
    WebSecurityCustomizer configureWebSecurity(){
        return (web) -> web.ignoring().requestMatchers("/images/**", "/css/**", "/javascript/**", "fonts/**");
    }

}


