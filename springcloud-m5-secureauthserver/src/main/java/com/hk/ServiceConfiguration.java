package com.hk;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Created by z003rv2s on 03.02.2017.
 */
@Configuration
public class ServiceConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("huseyin").password("pass1").roles("USER").and()
                .withUser("admin").password("pass2").roles("USER", "OPERATOR");
    }
}
