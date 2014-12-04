package no.uib.inf319.bordtennis.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public final class EmailSender {
    private static final String HOST = "localhost";
    private static final String FROM = "noreply@alster.uib.no";

    private EmailSender() {
    }

    public static void sendMail(final String subject, final String message,
            final String toAddress) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(HOST);
        email.setFrom(FROM);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(toAddress);
        email.send();
    }
}
