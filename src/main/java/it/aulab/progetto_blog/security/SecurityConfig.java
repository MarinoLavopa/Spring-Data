package it.aulab.progetto_blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity // per abilitare la sicurezza
public class SecurityConfig {

    private final static String cspDirectives = "default-src 'self' ; img-src 'self'; script-src 'self' cdn.jsdelivr.net 'unsafe-inline'; style-src 'self' cdn.jsdelivr.net cdnjs.cloudflare.com ; font-src cdnjs.cloudflare.com";

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(); // per criptare le password
    }

    @Bean
    public InMemoryUserDetailsManager userManager(){
        UserBuilder user = User.withUsername("user").password(encoder().encode("12345678"));
        UserBuilder admin = User.withUsername("admin").password(encoder().encode("admin12345678")); 
        return new InMemoryUserDetailsManager(user.build(), admin.build()); // Crea due utenti in memoria (user e admin) e li registra nel contesto di Spring Security
    }

    
    // SecurityFilterChain:è la "catena di filtri" che Spring Security applica ad ogni richiesta HTTP prima che arrivi al tuo controller.
    //(HttpSecurity http)è l'oggetto con cui configuri le regole di sicurezza.
    // http.authorizeHttpRequestsd dice a Spring: "per ogni richiesta in entrata, applica queste regole di autorizzazione".
    // Per ogni richiesta in entrata: se il path inizia con "/api/**" è pubblica e accessibile a tutti, altrimenti richiede autenticazione.
    // Customizer contiene un metodo di configurazione predefinita per la configurazione di formLogin.
    // @Bean 
    // public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {  
    //     http.authorizeHttpRequests( 
    //         (authorize)-> authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated()).formLogin(Customizer.withDefaults()); 
    //     return http.build();    
            
    // }


    // .loginPage("/login") per specificare una nuova pagina di login

     @Bean 
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {  
        http.authorizeHttpRequests( 
            (authorize)-> authorize.requestMatchers("/api/**").permitAll()
            .anyRequest().authenticated()).formLogin((formLogin)-> formLogin.loginPage("/login")
                .defaultSuccessUrl("/authors", true)
                .permitAll())
            .logout((logout)-> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/"))
            .csrf(
                        (csrf)-> csrf.ignoringRequestMatchers("/api/**"))
            .headers( // protezione per evitare l'attacco XSS
                        (headers)-> headers.xssProtection(Customizer.withDefaults())
                                           .contentSecurityPolicy(Customizer.withDefaults()).contentSecurityPolicy((csp)-> csp.policyDirectives(cspDirectives)));
        return http.build();    
            
    }


     

}
