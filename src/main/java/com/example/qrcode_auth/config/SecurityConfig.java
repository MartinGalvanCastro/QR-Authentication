package com.example.qrcode_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthProvider authProvider;


    public SecurityConfig(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .sessionManagement().maximumSessions(2).and()
                .and()
                .logout()
                .deleteCookies("auth_code", "JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .and()
                .authenticationProvider(authProvider)
                .authorizeHttpRequests((request)->request
                        .antMatchers(HttpMethod.GET,"/api","**/login","**/singup","**/","**/qr_sample").permitAll()
                        .antMatchers(HttpMethod.POST,"**/login","**/singup").permitAll()
                        .antMatchers(HttpMethod.GET,"**/logout","**/qr_data").authenticated()
                        .anyRequest().authenticated())
                .build();
    }
}
