package com.kein.ktech.service.impl;

import com.kein.ktech.domain.User;
import com.kein.ktech.repository.UserRepository;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User u =  userRepository.findAll().stream()
                .filter(user-> user.getEmail().equals(username))
                .findFirst()
                .orElseThrow(()->new UsernameNotFoundException("User Not Found!"));

        return new CustomUserDetails(u);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        var users = getUsers();
        for(User user : users)
        {
            if(user.getEmail().equals(email))return user;
        }
        return null;
    }

    @Override
    public void createNewUser(User user) {
        userRepository.createNewUser(user);
    }


    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private String siteUrl;
    @Override
    public void sendEmailVerification(CustomUserDetails user)
    {
        String link = siteUrl+"verify?code="+userRepository.findUserVerificationCodeById(user.getEmail());
        link+="&&userId="+user.getId();
        String subject = "Verification Email";
        String senderName = "KTech Team";
        String mailContents = "<b>This is email to verify your account !</b>";
        mailContents+="<br/><p>Please click to <br/>" +
                " <a href='"+link+"'>Verify</a> " +
                "<br/>to complete your registration!</p>";
        mailContents+="<b><i>Ktech Team</i></b>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("KTech", senderName);
            helper.setSubject(subject);
            helper.setTo(user.getEmail());
            helper.setText(mailContents,true);
            mailSender.send(message);
            System.out.println("Email is just sent!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean enableUser(User user) {
        return userRepository.enableUser(user);
    }


}
