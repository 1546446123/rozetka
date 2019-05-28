import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Utils {

    private static void saveToFile(List<String> items, String fname) throws IOException {
        FileWriter fw = new FileWriter(fname, true);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String s:items) {
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    }

    public static String saveToFile(List<String> items){
        String fname = String.format("%s.txt", Long.toString(System.currentTimeMillis()));
        try {
            saveToFile(items, fname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Saving results to:%s", fname));
        return fname;
    }

    public static void sendFileByEmail(String fname){
        List<String> recipients = null;
        try {
            recipients = Files.readAllLines(new File("recipients.txt").toPath(), Charset.defaultCharset() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendEmailWithAttachments(recipients, fname);
        System.out.println(String.format("Sending email with attach:%s", fname));
    }

    private static void sendEmailWithAttachments(List<String> recipints, String fname) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("xxxxxxx@gmail.com", "xxxxxx");
                    }
                });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("test@gmail.com"));
            for (String recipient:recipints) {

                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(recipient));
            }

            msg.setSubject(String.format("Test email with results and with attach - %s", fname));
            msg.setSentDate(new Date());

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            String textContent = "Here is result.";
            textPart.setText(textContent);
            multipart.addBodyPart(textPart);

            MimeBodyPart attachementPart = new MimeBodyPart();
            attachementPart.attachFile(new File(fname));
            multipart.addBodyPart(attachementPart);

            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("---Done---");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String saveProductToXLS(Map<String, String> items){
        String fname = String.format("%s.xlsx", Long.toString(System.currentTimeMillis()));
        ExcelWriter writer = new ExcelWriter();
        try {
            writer.saveToFile(items, fname);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Saving results to:%s", fname));
        return fname;
    }
    public static String saveProductToXLSEx(Map<String, String> items, Map<String, String> items2){
        String fname = String.format("%s.xlsx", Long.toString(System.currentTimeMillis()));
        ExcelWriter writer = new ExcelWriter();
        try {
            writer.saveToFileEx(items, items2, fname);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Saving results to:%s", fname));
        return fname;
    }

    public static void saveToDatabase(Map<String, String> items){
        MySqlWriter mySqlWriter = new MySqlWriter();
        mySqlWriter.saveToDatabase(items);
    }





}
