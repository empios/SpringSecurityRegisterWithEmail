package pl.wlodarczyk.springregistersecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wlodarczyk.springregistersecurity.models.Role;
import pl.wlodarczyk.springregistersecurity.models.User;
import pl.wlodarczyk.springregistersecurity.models.VeryficationToken;
import pl.wlodarczyk.springregistersecurity.repo.UserRepo;
import pl.wlodarczyk.springregistersecurity.repo.VeryficationTokenRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private VeryficationTokenRepo veryficationTokenRepo;
    private MailSenderService mailSenderService;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, VeryficationTokenRepo veryficationTokenRepo, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.veryficationTokenRepo = veryficationTokenRepo;
        this.mailSenderService = mailSenderService;
    }

    public void addNewUser(User user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        String token = UUID.randomUUID().toString();
        VeryficationToken veryficationToken = new VeryficationToken(token, user);
        veryficationTokenRepo.save(veryficationToken);

        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify?token=" + token;

        String adminUrl = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verifyAdmin?token=" + token;


        if (user.getRole() == Role.USER) {
            try {
                mailSenderService.sendMail(user.getEmail(), "Verify your account", url, false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else if (user.getRole() == Role.ADMIN) {
            User headAdmin = userRepo.findAllByUsername("empios");
            try {
                mailSenderService.sendMail(headAdmin.getEmail(), "Verify new Admin", adminUrl, false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            user.setRole(Role.USER);
            userRepo.save(user);
        }
    }

    public void verify(String token) {
        User user = veryficationTokenRepo.findByValue(token).getUser();
        user.setEnabled(true);
        userRepo.save(user);
        veryficationTokenRepo.delete(veryficationTokenRepo.findByValue(token));
    }

    public void switchToAdmin(String token) {
        User user = veryficationTokenRepo.findByValue(token).getUser();
        user.setRole(Role.ADMIN);
    }
}
