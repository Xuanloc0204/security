package com.example.demosecurity.config;

import com.example.demosecurity.controller.CustomAccessDeniedHandler;
import com.example.demosecurity.controller.CustomSuccessHandle;
import com.example.demosecurity.service.role.IAppRoleService;
import com.example.demosecurity.service.user.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private IAppUserService userService;
    @Autowired
    private IAppRoleService appRoleService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
//        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CustomSuccessHandle customSuccessHandle() {
        return new CustomSuccessHandle();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    //    xac thuc
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService((UserDetailsService) userService);
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        chuyen tu user trong db -  userdetail
//        builder partern
        UserDetails user = User.withUsername("user").password("123456").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("123456").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    //    phan quyen
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.
//        formLogin(Customizer.withDefaults())
//        .authorizeHttpRequests(
//                        auth -> auth
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/user/**").hasRole("USER")
//                                .anyRequest().authenticated()
//                )
//                .csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register","/login", "/css/**", "/js/**").permitAll() // Cho phép truy cập trang login
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Chỉ ADMIN mới truy cập được trang /admin/**
                        .requestMatchers("/user/**").hasRole("USER") // Chỉ USER mới truy cập được trang /user/**
                        .anyRequest().authenticated() // Tất cả các trang khác yêu cầu đăng nhập
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Định nghĩa đường dẫn logout
                        .logoutSuccessUrl("/login?logout=true") // Chuyển hướng đến login sau khi logout
                        .invalidateHttpSession(true) // Xóa session khi logout
                        .deleteCookies("JSESSIONID") // Xóa cookie session
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // ✅ Tắt CSRF nếu cần
        return http.build();
    }
}
