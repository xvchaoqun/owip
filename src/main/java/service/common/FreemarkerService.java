package service.common;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by fafa on 2016/10/29.
 */
@Service
public class FreemarkerService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public String process(String path, Map<String,Object> dataMap) throws IOException, TemplateException {

        StringWriter writer = new StringWriter();
        process(path, dataMap, writer);

        return writer.toString();
    }

    public void process(String path, Map<String,Object> dataMap, Writer out) throws IOException, TemplateException {

        Configuration cf = freeMarkerConfigurer.getConfiguration();
        Template tp= cf.getTemplate(path);
        tp.process(dataMap, out);
    }
}
