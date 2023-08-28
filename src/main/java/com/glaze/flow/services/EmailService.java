package com.glaze.flow.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.glaze.flow.constants.EmailLocalizationConstants;
import com.glaze.flow.entities.User;
import com.glaze.flow.events.SendEmailVerificationEvent;
import com.glaze.flow.utils.LocalizationMessageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final LocalizationMessageUtil messageUtil;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendAccountVerificationEmail(SendEmailVerificationEvent event) {
        User user = event.user();
        LOGGER.info("Sending account verification email for user with id {}", user.getId());

        Context context = new Context();
        context.setLocale(LocaleContextHolder.getLocale());
        context.setVariable("username", user.getUsername());
        context.setVariable("userId", user.getId());
        context.setVariable("token", event.token());
        context.setVariable("port", 8080);

        String emailContent = templateEngine.process("/emails/account-verification", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            String subject = messageUtil.getMessage(EmailLocalizationConstants.VERIFICATION_SUBJECT);
            helper.setSubject(subject);
            helper.setTo(user.getEmail());
            helper.setText(emailContent, true);

            mailSender.send(helper.getMimeMessage());
            LOGGER.info("Account verification email has been successfully sent to address {}", user.getEmail());
        }catch (MessagingException e) {
            LOGGER.info("Account verification email could have not been sent");
        }
    }

}
