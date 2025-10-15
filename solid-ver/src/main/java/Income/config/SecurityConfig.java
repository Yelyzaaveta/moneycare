package Income.config;

/*
  @author Orynchuk
  @project moneycare
  @class SecurityConfig
  @version 1.0.0
  @since 08.10.2025 - 22.44
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req
                        // Публічний ендпоінт
                        .requestMatchers("/api/v1/incomes/public").permitAll()

                        // CRUD доступ
                        .requestMatchers(HttpMethod.GET, "/api/v1/incomes/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/incomes/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/incomes/**").hasAnyRole("ADMIN", "SUPERADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/incomes/**").hasRole("SUPERADMIN")

                        // Тести-ендпоінти
                        .requestMatchers("/api/v1/incomes/user").hasRole("USER")
                        .requestMatchers("/api/v1/incomes/admin").hasRole("ADMIN")
                        .requestMatchers("/api/v1/incomes/superadmin").hasRole("SUPERADMIN")
                        .requestMatchers("/api/v1/incomes/mixed").hasAnyRole("ADMIN","SUPERADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder()
                .username("admin")
                .password(SecurityConfig.passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails superadmin = User.builder()
                .username("superadmin")
                .password(passwordEncoder().encode("superadmin"))
                .roles("SUPERADMIN")
                .build();


        return new InMemoryUserDetailsManager(admin, user, superadmin);
    }
}
