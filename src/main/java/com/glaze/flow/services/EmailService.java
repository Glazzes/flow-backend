package com.glaze.flow.services;

import com.glaze.flow.entities.User;
import com.glaze.flow.events.SignUpEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendAccountVerificationEmail(User user, String token) {
        LOGGER.info("Sending account verification email for user with id {}", user.getId());

        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("userId", user.getId());
        context.setVariable("token", token);
        context.setVariable("port", 8080);

        String emailContent = templateEngine.process("/emails/account-verification", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setSubject("Flow verify your account");
            helper.setTo(user.getEmail());
            helper.setText(emailContent, true);

            mailSender.send(helper.getMimeMessage());
            LOGGER.info("Account verification email has been successfully sent to address {}", user.getEmail());
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
