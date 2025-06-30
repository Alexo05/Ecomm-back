package org.usermicroservice.emails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.usermicroservice.dtos.UserDTO;
import org.usermicroservice.entities.User;

@Service
@Slf4j
@AllArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendConfirmationEmail(User confirmationToken, String senderEmail) throws MessagingException {
        //MIME - HTML message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(senderEmail);
        helper.setTo(confirmationToken.getEmail());
        helper.setSubject("Confirm Your Email - E-Commerce Application Registration");
        helper.setText("<html>" +
                        "<body>" +
                        "<h2>Dear "+ confirmationToken.getFirstname() + ",</h2>"
                        + "<br/> We're excited to have you get started. " +
                        "Please click the link below to confirm your account."
                        + "<br/> "  + generateConfirmationLink(confirmationToken.getConfirmationToken())+" " +
                        "<br/> Regards,<br/>" +
                        "E-Commerce Registration Team" +
                        "</body>" +
                        "</html>"
                , true);
        javaMailSender.send(message);
        log.info("Confirmation email sent to {}", confirmationToken.getEmail());
    }

    private String generateConfirmationLink(String token){
        return "<a href=http://localhost:8085/auth/confirm-account?token="+token+">Confirm Email</a>";
    }


    @Override
    public void sendResetPasswordMail(String to, String subject,  UserDTO userDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("ecommercemicroservice2024@gmail.com");
        helper.setTo(to);
        helper.setSubject("Reset Your Email - E-Commerce Application Registration");
        helper.setText("<html>" +
                        "<body>" +
                        "<h2>Dear "+ userDTO.getFirstname() + ",</h2>"
                        + "<br/> We have sent you this email in response to your password reset request. " +
                        " <br/> To reset your password, please follow the link below:  "
                        + "<a href=http://localhost:4200/change-password?token="+userDTO.getResetPasswordToken()+">Reset Password</a>" +
                        "<br/> Regards,<br/>" +
                        "E-Commerce Registration Team" +
                        "</body>" +
                        "</html>"
                , true);
        javaMailSender.send(message);
        log.info("Reset password email sent to {}", to);
    }

    private String generateResetPasswordLink(String token){
        return "<a href=http://http://localhost:4200/change-password?token="+token+">Reset Password</a>";
    }

}
