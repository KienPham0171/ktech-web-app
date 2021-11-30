package com.kein.ktech.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kein.ktech.security.CustomOauth2UserServices;
import com.kein.ktech.security.handler.LocalLoginSuccessHandler;
import com.kein.ktech.security.handler.Oauth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Bean(name = "siteUrl")
    public String siteUrl()
    {
        return "http://localhost:8080/";
    }



    @Value("${cloudName}")
    private String cloudName;
    @Value("${apiKey}")
    private String apiKey;
    @Value("${apiSecret}")
    private String apiSecret;
    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }


    @Bean
    public
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomOauth2UserServices customOauth2UserService;
    @Autowired
    private Oauth2SuccessHandler oauth2SuccessHandler;
    @Autowired private LocalLoginSuccessHandler localLoginSuccessHandler;

    @Bean
    public JavaMailSender javaMailSender()
    {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("kienpham.se.mailService@gmail.com");
        mailSender.setPassword("kZ@phamse10");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        var facebookClient = clientRegistrationFacebook();
        var googleClient = clientRegistrationGoogle();
        return new InMemoryClientRegistrationRepository(facebookClient, googleClient);
    }
    public ClientRegistration clientRegistrationFacebook() {

        String url = "https://www.freeprivacypolicy.com/live/aad14450-2e07-4161-95e3-dc2efa2d12be";
        return CommonOAuth2Provider.FACEBOOK
                .getBuilder("facebook")
                .clientId("3015792782011259").clientSecret("eaf790feb66cebe44b6f876a12da00fb")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .build();

    }

    public ClientRegistration clientRegistrationGoogle() {
        String id = "397242900572-7jbjefhjfuk8ei627298ba6eje5dec3i.apps.googleusercontent.com";
        String sec = "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";
        return CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId(id).clientSecret(sec)
                .build();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("http://127.0.0.1:5500/", "http://localhost:8080/"));
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE"));
                return config;
            };
            c.configurationSource(source);
        });
        http.formLogin().loginPage("/login").permitAll()
                        .successHandler(localLoginSuccessHandler)
                        .and().oauth2Login().loginPage("/login")
                        .userInfoEndpoint().userService(customOauth2UserService)
                        .and().successHandler(oauth2SuccessHandler)
                        .and()
                        .logout().logoutUrl("/logout").deleteCookies("JSESSIONID")
                        .and().logout().invalidateHttpSession(true)
                        .and().logout().logoutSuccessUrl("/home").permitAll();
//        http
//                .logout(logout -> logout
//                        .logoutUrl("logout")
//                        .addLogoutHandler(new SecurityContextLogoutHandler())
//                );
        http.authorizeRequests()
                .mvcMatchers("/user/**").hasAuthority("user")
                .mvcMatchers("/admin/**").hasAuthority("admin")
                .mvcMatchers("/","/css/**","/images/**","/js/**","/api/**").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();

    }

}
