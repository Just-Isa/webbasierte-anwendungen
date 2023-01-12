package de.hsrm.mi.web.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration @EnableWebSecurity
public class ProjektSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired ProjektUserDetailService myUserDetailsService;

    @Bean PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authmanagerbuilder) throws Exception {
        PasswordEncoder pwenc = passwordEncoder();

        authmanagerbuilder.inMemoryAuthentication()
            .withUser("friedfert")
            .password(pwenc.encode("dingdong"))
            .roles("USER")
        .and()
            .withUser("joghurta")
            .password(pwenc.encode("chefin"))
            .roles("ADMIN");

        authmanagerbuilder
                    .userDetailsService(myUserDetailsService)
                    .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().disable();
        http.authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/stompbroker/**").permitAll()
            .antMatchers("/registrieren", "/logout").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.GET).hasAnyRole("ADMIN", "USER")
        .and()
            .formLogin()
            .defaultSuccessUrl("/benutzerprofil")
            .permitAll()
        .and()
            .logout()
            .logoutSuccessUrl("/login")
            .permitAll();
    }
}