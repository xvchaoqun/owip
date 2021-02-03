package freemarker;

import bean.MetaClassOption;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.CadreEdu;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import persistence.cadre.CadreEduMapper;
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

    // 干部简历上全日制/在职学习经历结尾对应的表述
    public static String getEduSuffix(Integer eduId, boolean isOnjob){

        if(eduId==null) return null;

        String suffix = "";
        MetaType metaType = CmTag.getMetaType(eduId);
        if(metaType==null) return null;

        MetaClass eduCls = CmTag.getMetaClassByCode("mc_edu");
        Map<String, MetaClassOption> options = eduCls.getOptions();

        MetaClassOption option = options.get(metaType.getExtraAttr());
        if(option!=null){
            suffix = isOnjob?option.getDetail():option.getName();
        }

        return suffix;
    }

    @Override  
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
            TemplateDirectiveBody body) throws TemplateException, IOException {

        Writer out = env.getOut();
        Integer cadreEduId = DirectiveUtils.getInt("cadreEduId", params);

        CadreEduMapper cadreEduMapper = CmTag.getBean(CadreEduMapper.class);
        CadreEdu cadreEdu = cadreEduMapper.selectByPrimaryKey(cadreEduId);
        Integer eduId = cadreEdu.getEduId();
        MetaType eduType = CmTag.getMetaType(eduId);
        boolean isOnjob = (eduType!=null && StringUtils.equals(eduType.getCode(), "mt_onjob"));

        String eduSuffix = getEduSuffix(eduId, isOnjob);
        if(eduSuffix!=null) {
            out.write(getEduSuffix(eduId, isOnjob));
            if (body != null) {
                body.render(out);
            }
        }
    }
}  