package com.joaovictor.PhegonHotel.security;

import com.joaovictor.PhegonHotel.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityCOnfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {// Configuração de segurança
        httpSecurity.csrf(AbstractHttpConfigurer::disable)// Disabilita o csrf
                .cors(Customizer.withDefaults())// Habilita o cors
                .authorizeHttpRequests(request -> request// Configura as rotas que precisam de autenticação
                        .requestMatchers("/auth/**", "/rooms/**","/bookings/**").permitAll()// Permite o acesso a essas rotas
                        .anyRequest().authenticated())// Qualquer outra rota precisa de autenticação
                .sessionManagement(manager -> manager// Configura o gerenciamento de sessão
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Desabilita o uso de sessões
                .authenticationProvider(authenticationProvider())// Configura o provedor de autenticação
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);// Adiciona o filtro de autenticação

        return httpSecurity.build();// Retorna a configuração
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {// Configura o provedor de autenticação
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();// Cria um provedor de autenticação
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);// Configura o serviço de detalhes do usuário
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());// Configura o codificador de senha
        return daoAuthenticationProvider;// Retorna o provedor de autenticação
    }

    @Bean
    public PasswordEncoder passwordEncoder() {// Configura o codificador de senha
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {// Configura o gerenciador de autenticação
        return authenticationConfiguration.getAuthenticationManager();
    }
}
