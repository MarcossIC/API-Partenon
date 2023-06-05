package dev.partenon.global.domain.abstractcomponents;

import java.io.File;

public interface MailPort {
    Boolean send(String to, String subject, String textMessage);

    Boolean send(String to, String subject, String textMessage, File... attachments);
}
