package com.kein.ktech.controller;

import com.kein.ktech.domain.User;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.CategoryService;
import com.kein.ktech.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService catService;

//    @GetMapping("/")
//    public String welcome(Model model)
//    {
//        model.addAttribute("cats",catService.getCategories());
//        return "home1";
//    }
    @GetMapping("/login")
    public String login()
    {
        /*SecurityContext ct = SecurityContextHolder.getContext();
        Authentication au =  ct.getAuthentication();
        if(au != null)
        {
            System.out.println(au.getName());
            return "home";
        }*/
        return "login";
    }

    @GetMapping("/active")
    public String active(@RequestParam(name = "sendEmail",defaultValue = "false") boolean sendEmail,
                         Authentication au, Model model)
    {
        if (sendEmail) {
            model.addAttribute("sent","sent");
            CustomUserDetails user = (CustomUserDetails)au.getPrincipal();
            userService.sendEmailVerification(user);
        }

        return "active";
    }

    @GetMapping("/verify")
    public String sendEmail(@RequestParam(name = "code") String code,
                            @RequestParam(name = "userId") long userId)
    {

        User user = userService.findUserById(userId);
        if(user.getVerificationCode().compareTo(code)==0)
        {
            userService.enableUser(user);
            return "successPage";
        }else{
            return "errorPage";
        }
    }
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
