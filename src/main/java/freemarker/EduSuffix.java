package freemarker;

import bean.MetaClassOption;
import domain.base.MetaClass;
import domain.base.MetaType;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import sys.freemarker.DirectiveUtils;
import sys.tags.CmTag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 学习经历中学历的表述
 * 专科、大学本科->学生
 *  其他默认
 */
public class EduSuffix implements TemplateDirectiveModel {

    // 干部简历上全日制学历对应的描述
    public static String getEduSuffix(Integer eduId){

        if(eduId==null) return null;

        String suffix = "";
        MetaType metaType = CmTag.getMetaType(eduId);
        if(metaType==null) return null;

        MetaClass eduCls = CmTag.getMetaClassByCode("mc_edu");
        Map<String, MetaClassOption> options = eduCls.getOptions();

        MetaClassOption option = options.get(metaType.getExtraAttr());
        if(option!=null){
            suffix = option.getName();
        }

        return suffix;
    }

    // 干部简历上在职教育学历对应的描述
    public static String getEduSuffix2(Integer eduId){

        if(eduId==null) return null;

        String suffix = "";
        MetaType metaType = CmTag.getMetaType(eduId);
        if(metaType==null) return null;

        MetaClass eduCls = CmTag.getMetaClassByCode("mc_edu");
        Map<String, MetaClassOption> options = eduCls.getOptions();

        MetaClassOption option = options.get(metaType.getExtraAttr());
        if(option!=null){
            suffix = option.getDetail();
        }

        return suffix;
    }

    @Override  
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
            TemplateDirectiveBody body) throws TemplateException, IOException {

        Writer out = env.getOut();
        Integer eduId = DirectiveUtils.getInt("eduId", params);

        String eduSuffix = getEduSuffix(eduId);
        if(eduSuffix!=null) {
            out.write(getEduSuffix(eduId));
            if (body != null) {
                body.render(out);
            }
        }
    }
}  