package freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import sys.freemarker.DirectiveUtils;
import sys.utils.DateUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

/**
    两个日期间隔天数
 */
public class DayCountSuffix implements TemplateDirectiveModel {

    @Override  
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
            TemplateDirectiveBody body) throws TemplateException, IOException {

        Writer out = env.getOut();
        Date start = DirectiveUtils.getDate("start", params);
        Date end = DirectiveUtils.getDate("end", params);

        Integer days = DateUtils.getDayCountBetweenDate(start, end);
        if(days!=null) {
            out.write(String.valueOf(days));
            if (body != null) {
                body.render(out);
            }
        }
    }
}  