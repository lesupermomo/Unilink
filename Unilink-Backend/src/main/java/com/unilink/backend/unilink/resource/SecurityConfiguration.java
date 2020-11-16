package com.unilink.backend.unilink.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unilink.backend.unilink.service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    UserDetailsService userDetailsService;


    @Autowired
	private MyUserDetailsService myUserDetailsService;

    @Autowired
	private JwtRequestFilter jwtRequestFilter;

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests()
//        http.csrf().disable().authorizeRequests()
//        		.antMatchers("/authenticate").permitAll()
//                .antMatchers("/admin").authenticated()
//
//                .antMatchers("/user").authenticated()
//                .antMatchers("/").permitAll()
//                .antMatchers("/allUsers").permitAll()
//                .and().formLogin();

        //to only allow certain roles: //                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
        http.csrf().disable().authorizeRequests().
        		antMatchers("/authenticate").permitAll().
        		antMatchers("/").permitAll().
        		antMatchers("/admin").permitAll().
        		antMatchers("/loadUser").permitAll().
        		antMatchers("/loginUser").permitAll().
                //antMatchers("/allUsers").permitAll().
                anyRequest().authenticated().and().//formLogin();
                formLogin().loginPage("/admin").and(). /** set custom login page*/
				exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    
    
}