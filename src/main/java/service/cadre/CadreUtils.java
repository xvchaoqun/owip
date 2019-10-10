package service.cadre;

import bean.ResumeRow;
import controller.global.OpException;
import org.apache.commons.lang3.StringUtils;
import sys.utils.DateUtils;
import sys.utils.PatternUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lm on 2018/4/27.
 */
public class CadreUtils {

    // 处理专业
    public static String major(String major){

        if(StringUtils.isNotBlank(major)) {
            if(!major.endsWith("专业")){
                major = StringUtils.trim(major) + "专业";
            }
            return major;
        }

        return null;
    }

    // 解析中组部审批表的简历部分
    public static List<ResumeRow> parseResume(String resume){

        int row = 1;
        List<ResumeRow> resumeRows = new ArrayList<>();
        String[] lines = resume.split("\n");
        for (String line : lines) {

            // 读取每行经历
            line = line.trim();
            ResumeRow newRow = new ResumeRow();
            String subLine = null;
            if(PatternUtils.match("\\s*[（|\\(].*", line)){ // 如果是换行的其间经历
                newRow.fRow = row-1;
                newRow.isEduWork = resumeRows.get(resumeRows.size()-1).isEdu;
            }else{
                newRow.row = row++;

                // 提取其间经历
                subLine = PatternUtils.withdraw(".*([（|\\(].*[\\-—]{1,2}.*[）|\\)]).*", line);
                if (StringUtils.isNotBlank(subLine)) {
                    line = line.replace(subLine, "");
                }
            }

            parseResumeRow(newRow, line);
            resumeRows.add(newRow);

            // 处理其间经历
            if (StringUtils.isNotBlank(subLine)) {
                subLine = subLine.trim();

                subLine = PatternUtils.withdraw("[（|\\(](.*[\\-—]{1,2}.*)[）|\\)]", subLine);
                String[] subLines = subLine.split("；");
                for (String sub : subLines) {
                    sub = sub.trim().replace("[其|期]间[：|:]", "");
                    ResumeRow r = new ResumeRow();
                    r.fRow = newRow.row;
                    r.isEduWork = newRow.isEdu;
                    parseResumeRow(r, sub);
                    resumeRows.add(r);
                }
            }
        }

        return resumeRows;
    }

    private static void parseResumeRow(ResumeRow r, String content) {

        if(PatternUtils.match("\\s*[（|\\(].*", content)) { // 如果是换行的其间经历， 先去掉最外层的括号
            content = PatternUtils.withdraw("[（|\\(](.*)[）|\\)]", content);
        }

        String _times = PatternUtils.withdraw("([0-9]{4}\\.[0-9]{2}[\\-—]{1,2}([0-9]{4}\\.[0-9]{2})?)", content);
        if(StringUtils.isBlank(_times)){
            throw new OpException("第{0}行{1}简历读取时间为空", r.row==null?r.fRow:r.row, r.row==null?"其间":"");
        }
        List<String> times = PatternUtils.withdrawAll("([0-9]{4}\\.[0-9]{2})", _times);
        if (times.size() >= 2) {
            r.start = DateUtils.parseStringToDate(times.get(0));
            r.end = DateUtils.parseStringToDate(times.get(1));
        } else if (times.size() == 1) {
            r.start = DateUtils.parseStringToDate(times.get(0));
        }

        if(r.start==null){
            throw new OpException("第{0}行{1}简历读取起始时间为空", r.row==null?r.fRow:r.row, r.row==null?"其间":"");
        }

        String desc = PatternUtils.withdraw("[0-9]{4}\\.[0-9]{2}[\\-—]{1,2}([0-9]{4}\\.[0-9]{2})?\\s*(.*)",
                    content, 2);
        r.desc = desc;

        if(StringUtils.isBlank(desc)){
            throw new OpException("第{0}行{1}简历读取为空", r.row==null?r.fRow:r.row, r.row==null?"其间":"");
        }

        // 判断是否是学习经历
        r.isEdu = (StringUtils.containsAny(desc,
                "学习", "进修", "毕业", "中专", "大专", "专科", "学士", "硕士", "博士", "学位")
        || desc.endsWith("学生")|| desc.endsWith("本科")|| desc.endsWith("本科生")|| desc.endsWith("研究生"));

        // 博士后算工作经历
        if(r.isEdu && StringUtils.contains(desc, "博士后")){
           r.isEdu = false;
        }

        //System.out.println("desc = " + desc + "      "  + r.isEdu);
    }
}
