package com.joaovictor.PhegonHotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {//Configuração de CORS
                registry.addMapping("/**")//Permite que todas as rotas tenham acesso
                        .allowedMethods("GET", "POST", "PUT", "DELETE")//Permite os métodos GET, POST, PUT e DELETE
                        .allowedOrigins("*");//Permite que qualquer origem tenha acesso
            }
        };
    }
}
