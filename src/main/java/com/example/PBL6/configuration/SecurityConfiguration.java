package com.example.PBL6.configuration;

import com.example.PBL6.persistance.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // all access http must be authorized config
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers("/user/register/**", "/user/login/**", "/category/all/**",
                        "/product/all", "/product/detail/**", "/payment/paymentResult/**")
                .permitAll()
                .requestMatchers("/user/profile/**", "/cart/**", "/favouriteProduct/**",
                        "/payment/createPayment/**", "/order/createOrder/**", "/order/getOrders/**")
                .hasAnyAuthority(UserRole.CUSTOMER.name(), UserRole.ADMIN.name())
                .requestMatchers("/user/all/**", "/category/add/**", "/category/delete/**",
                        "/product/add/**", "/product/update/**", "/user/customerOrders")
                .hasAuthority(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
