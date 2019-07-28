package service.cadre;

import bean.ResumeRow;
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

            // 先读取主要经历
            ResumeRow mainRow = new ResumeRow();
            line = line.trim();
            if(PatternUtils.match("\\s*[（|\\(].*", line)){ // 如果是换行的其间经历
                mainRow.fRow = row;
            }else{
                mainRow.row = row++;
            }
            //System.out.println("re = " + re);
            String subLine = PatternUtils.withdraw(".*([（|\\(].*--.*[）|\\)]).*", line);
            //System.out.println("withdraw = " + withdraw);
            if (StringUtils.isNotBlank(subLine)) {
                line = line.replace(subLine, "");
            }

            parseResumeRow(mainRow, line);
            resumeRows.add(mainRow);

            // 再读取其间经历
            if (StringUtils.isNotBlank(subLine)) {
                subLine = subLine.trim();

                subLine = PatternUtils.withdraw("[（|\\(](.*--.*)[）|\\)]", subLine);
                String[] subLines = subLine.split("；");
                for (String sub : subLines) {
                    sub = sub.trim().replace("其间：", "");
                    //System.out.println("wd = " + wd);
                    ResumeRow r = new ResumeRow();
                    r.fRow = mainRow.row;
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

        List<String> times = PatternUtils.withdrawAll("([0-9]{4}\\.[0-9]{2})", content);
        if (times.size() == 2) {
            r.start = DateUtils.parseStringToDate(times.get(0));
            r.end = DateUtils.parseStringToDate(times.get(1));
        } else if (times.size() == 1) {
            r.start = DateUtils.parseStringToDate(times.get(0));
        }
        String desc = PatternUtils.withdraw("[0-9]{4}\\.[0-9]{2}-{1,2}([0-9]{4}\\.[0-9]{2})?\\s*(.*)",
                    content, 2);
        r.desc = desc;

        // 判断是否是学习经历
        r.isEdu = (StringUtils.containsAny(desc,
                "学习", "毕业", "中专", "大专", "专科", "学士", "硕士", "博士", "学位")
        || desc.endsWith("学生")|| desc.endsWith("本科")|| desc.endsWith("本科生")|| desc.endsWith("研究生"));

    }
}
