package dev.partenon.global.infrastructure.adapters;

import dev.partenon.global.domain.abstractcomponents.MailPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
@Setter
@Getter
public class MailService implements MailPort {
    private static final File[] NO_ATTACHMENTS = null;
    @Autowired
    private JavaMailSender sender;

    /**
     * Configuracion del sender
     */
    @Value("${spring.mail.username}")
    private String from;

    /*
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private String port;
    @PostConstruct
    protected void init() {
        sender.setPort(Integer.parseInt(port));
        sender.setPassword(password);
        sender.setUsername(from);
    }
*/
    @Override
    public Boolean send(String to, String subject, String textMessage) {
        var flag = this.send(to, subject, textMessage, NO_ATTACHMENTS);
        return flag;
    }

    @Override
    public Boolean send(String to, String subject, String textMessage, File... attachments) {
        var send = false;
        // chequeo de parámetros
        Assert.hasLength(to, "email 'to' needed");
        Assert.hasLength(subject, "email 'subject' needed");
        Assert.hasLength(textMessage, "email 'text' needed");
        final MimeMessage message = sender.createMimeMessage();
        try {
            // el flag a true indica que va a ser multipart
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // settings de los parámetros del envío
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(getFrom());
            helper.setText(textMessage);

            // adjuntando los ficheros
            if (attachments != null) {
                for (int i = 0; i < attachments.length; i++) {
                    FileSystemResource file = new FileSystemResource(attachments[i]);
                    helper.addAttachment(attachments[i].getName(), file);
                    if (log.isDebugEnabled()) {
                        log.debug("File '" + file + "' attached.");
                    }
                }
            }
            log.info("Llegue hasta aqui");
            this.sender.send(message);
            send = true;
        } catch (Exception e) {
            log.info("entre al catch");
            e.printStackTrace();
        }
        log.info("Antes del return, send is: {}", send);
        return send;
    }

}
