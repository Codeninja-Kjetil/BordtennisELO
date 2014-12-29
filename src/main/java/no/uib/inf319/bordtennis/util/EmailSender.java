package no.uib.inf319.bordtennis.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Utility class used to send e-mails.
 *
 * @author Kjetil
 */
public final class EmailSender {
    /**
     * E-mail host.
     */
    private static final String HOST = "localhost";
    /**
     * E-mail "from" address.
     */
    private static final String FROM = "noreply@alster.uib.no";

    /**
     * Private constructor.
     */
    private EmailSender() {
    }

    /**
     * Sends a email to the specified addresses with the specified subject and
     * content.
     *
     * @param subject e-mail subject
     * @param message e-mail content
     * @param toAddresses e-mail "to" addresses
     * @throws EmailException @see{EmailException}
     */
    public static void sendMail(final String subject, final String message,
            final String... toAddresses) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(HOST);
        email.setFrom(FROM);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(toAddresses);
        email.send();
    }
}
