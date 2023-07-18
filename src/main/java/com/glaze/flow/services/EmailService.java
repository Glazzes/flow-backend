package com.glaze.flow.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendAccountVerificationEmail(String email, Long userId) {
        Context context = new Context();
        context.setVariable("username", "glaze");
        context.setVariable("userId", userId);
        context.setVariable("port", 8080);

        String emailContent = templateEngine.process("/emails/account-verification", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setSubject("Flow verify your account");
            helper.setTo(email);
            helper.setText(emailContent, true);

            mailSender.send(helper.getMimeMessage());
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
