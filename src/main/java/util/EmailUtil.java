package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.util.Properties;

public class EmailUtil {

    // Sends an invoice email with a PDF attachment
    public static boolean sendInvoiceEmail(String toEmail, String subject, String bodyMessage, String attachmentPath) {
        final String fromEmail = "hasarangasamarakoon@gmail.com"; 
        final String appPassword = "qmpw tdyo uyzo ijcz"; // Use an App Password for Gmail

        // SMTP configuration
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            // Email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Pahana Edu Bookshop")); // 🏷️ Custom display name
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(bodyMessage);

            // Attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File(attachmentPath);
            if (!file.exists()) {
                System.err.println("❌ Attachment not found: " + attachmentPath);
                return false;
            }
            attachmentPart.attachFile(file);
            attachmentPart.setFileName(file.getName());

            // Combine parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send it
            Transport.send(message);
            System.out.println("✅ Email sent to: " + toEmail);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
