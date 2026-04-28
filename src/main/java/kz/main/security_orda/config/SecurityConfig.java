package kz.main.security_orda.config;

import kz.main.security_orda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            var user  = userRepository.findUserByEmail(username);

            if(user==null)throw new UsernameNotFoundException("User Not Found");
            else return user;
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security){

        var auth = security.getSharedObject(AuthenticationManagerBuilder.class);

            auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

            security.csrf(AbstractHttpConfigurer::disable);

            security.exceptionHandling(e-> e.accessDeniedPage("/" +
                    "forbidden"));

            security.formLogin(fl->
                    fl.loginProcessingUrl("/auth")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .loginPage("/sign-in")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/sign-in?error"));

            security.logout(lg-> lg.logoutUrl("/log-out").logoutSuccessUrl("/sign-in"));

            return security.build();
    }

}
