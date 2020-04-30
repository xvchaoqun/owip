package freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import sys.freemarker.DirectiveUtils;
import sys.utils.NumberUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
/**
    数字转换汉语
 */
public class ToHanStrSuffix implements TemplateDirectiveModel {
    @Override  
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
            TemplateDirectiveBody body) throws TemplateException, IOException {

        Writer out = env.getOut();
        int number = DirectiveUtils.getInt("number",params);

        String numberStr = NumberUtils.toHanStr(number);
        if (StringUtils.isNotBlank(numberStr)){
            out.write(numberStr);
            if (body!=null) {
                body.render(out);
            }
        }
    }
}  