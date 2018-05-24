package freemarker;

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

    public static String getEduSuffix(Integer eduId){

        if(eduId==null) return null;

        String suffix = "";
        MetaType metaType = CmTag.getMetaType(eduId);
        if(metaType==null) return null;

        switch (metaType.getCode()) {
            case "mt_edu_zk":
            case "mt_edu_bk":
                suffix = "学生";
                break;
            case "mt_edu_master":
                suffix = "硕士研究生";
                break;
            case "mt_edu_yjskcb":
                suffix = "硕士研究生（研究生课程班）";
                break;
            case "mt_edu_sstd":
                suffix = "硕士研究生（硕士同等学历）";
                break;
            case "mt_edu_doctor":
                suffix = "博士研究生";
                break;
        }

        return suffix;
    }
    public static String getEduSuffix2(Integer eduId){

        if(eduId==null) return null;

        String suffix = "";
        MetaType metaType = CmTag.getMetaType(eduId);
        if(metaType==null) return null;

        switch (metaType.getCode()) {
            case "mt_edu_zk":
                suffix = "专科";
                break;
            case "mt_edu_bk":
                suffix = "本科";
                break;
            case "mt_edu_master":
            case "mt_edu_yjskcb":
            case "mt_edu_sstd":
            case "mt_edu_doctor":
                suffix = "研究生";
                break;
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