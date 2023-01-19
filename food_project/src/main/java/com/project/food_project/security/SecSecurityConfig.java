package com.project.food_project.security;

import com.project.food_project.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecSecurityConfig {

    @Autowired
    private CustomAuthentProvider customAuthentProvider;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthentProvider);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/signin").permitAll()
//                .antMatchers("/signup").permitAll()
//                .antMatchers("/oauth2/**").permitAll()
//                .antMatchers("/signup/**").permitAll()
//                .antMatchers("/refresh-token").permitAll()
//                .antMatchers("/file/**").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/signin/test").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().permitAll()
                .defaultSuccessUrl("/signin/text");
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .addLogoutHandler(new SecurityContextLogoutHandler())
//                .addLogoutHandler
//                        ((request, response, authentication) -> response.setHeader("Authorization",""))
//                .addLogoutHandler(new HeaderWriterLogoutHandler(
//                        new ClearSiteDataHeaderWriter(CACHE, COOKIES, STORAGE)));

//        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId("549499742605-n8hvguefoic2ih90u566vt1qeh97d4fm.apps.googleusercontent.com")
                .clientSecret("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").build());
    }

}
