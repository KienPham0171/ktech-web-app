package com.kein.ktech.security.handler;

import com.kein.ktech.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LocalLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        if(user.isAdmin()) {

            response.sendRedirect("/admin/dashboard");
            return;
        }
        if(!user.isActive())
        {
            response.sendRedirect("/active");
        }else{
            response.sendRedirect("/home");
        }

    }
}
