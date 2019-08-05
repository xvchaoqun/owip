package service.base;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lm on 2018/6/1.
 */
@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void send(String email, String subject, String from, Map<String, Object> model) throws IOException, TemplateException {

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp = cf.getTemplate("email/email.ftl");

        final String text = FreeMarkerTemplateUtils.processTemplateIntoString(tp, model);

        mailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setTo(email);
            message.setFrom(from);
            message.setCc(from);
            message.setSubject(subject);
            message.setText(text, true);
        });
    }
}
