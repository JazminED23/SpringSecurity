package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/sec/index2").permitAll()
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .permitAll());
                //.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/sec/index2").permitAll()
                .anyRequest().authenticated())
                 .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                         .successHandler(successHandler()) //URL a donde se irá despues de iniciar seción
                         .permitAll())
                 .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                         .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //ALWAYS - IF_REQUIRED - NEVER - STATELESS
                         .invalidSessionUrl("/login")
                         .maximumSessions(1)
                         .expiredUrl("/login")
                                 .sessionRegistry(sessionRegistry())
                         )
                 .sessionManagement(session -> session
                         .sessionFixation(sessionFixation -> sessionFixation
                                 .migrateSession()
                         )

                 )
                 .httpBasic(Customizer.withDefaults())

                ;
                return  httpSecurity.build();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
           response.sendRedirect("/sec/session");
        });
    }
}
