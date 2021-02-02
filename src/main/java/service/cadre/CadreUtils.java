package service.cadre;

import bean.ResumeRow;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import sys.utils.ContentUtils;
import sys.utils.DateUtils;
import sys.utils.PatternUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static List<ResumeRow> parseResume(String resume, String realname){

        int row = 1;
        List<ResumeRow> resumeRows = new ArrayList<>();
        String[] lines = resume.split("\n");
        List<String> lineList = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {

            if(StringUtils.isBlank(lines[i]))continue;

            if(i>0 && !PatternUtils.match("[\\s]*[0-9]{4}\\.[0-9]{2}[\\-—～－]{1,2}([0-9]{4}\\.[0-9]{2})?([\\u4e00-\\u9fa5]{2})?\\s+.*", lines[i])){
                int lastIdx = lineList.size()-1;
                lineList.set(lastIdx, StringUtils.trimToEmpty(lineList.get(lastIdx))+StringUtils.trimToEmpty(lines[i]));
            }else{
                lineList.add(lines[i]);
            }
        }

        for (String line : lineList) {

            // 读取每行经历
            line = ContentUtils.trimAll(line);
            ResumeRow newRow = new ResumeRow();
            String subLine = null;
            if(PatternUtils.match("\\s*[（|\\(].*", line)){ // 如果是换行的其间经历
                newRow.fRow = row-1;
                newRow.isEduWork = resumeRows.get(resumeRows.size()-1).isEdu;
            }else{
                newRow.row = row++;

                // 提取其间经历
                subLine = PatternUtils.withdraw("([（|\\(]([其|期]间[：|:])?[0-9]{4}\\.[0-9]{1,2}[\\-—～－]{1,2}.+[）|\\)])[;|；|\\s]?", line);
                if (StringUtils.isNotBlank(subLine)) {
                    line = line.replace(subLine, "");
                }
            }

            if (parseResumeRow(newRow, line, realname)==1)
                continue;
            resumeRows.add(newRow);

            // 处理其间经历
            if (StringUtils.isNotBlank(subLine)) {
                subLine = subLine.trim();

                subLine = PatternUtils.withdraw("[（|\\(](.*[0-9]{4}(\\.[0-9]{2})?([\\-—～－]{1,2})?.*)[）|\\)]", subLine);
                subLine = subLine.replaceAll("[)|）][(|（]", "；");
                String[] subLines = subLine.split("；|;");
                for (String sub : subLines) {

                    //sub = sub.trim().replace("[其|期]间[：|:]", "");
                    sub = RegExUtils.removePattern(sub.trim(), "[其|期]间[：|:]");
                    ResumeRow r = new ResumeRow();
                    r.fRow = newRow.row;
                    r.isEduWork = newRow.isEdu;
                    if(parseResumeRow(r, sub, realname) == 1){
                        newRow.note = sub;
                        continue;
                    }
                    resumeRows.add(r);
                }
            }
        }

        return resumeRows;
    }

    private static int parseResumeRow(ResumeRow r, String content, String realname) {

        if(StringUtils.isBlank(content)) return 0;

        if(PatternUtils.match("\\s*[（|\\(].*", content)) { // 如果是换行的其间经历， 先去掉最外层的括号
            content = PatternUtils.withdraw("[（|\\(](.*)[）|\\)]", content);
        }

        String _times = PatternUtils.withdraw("([0-9]{4}\\.[0-9]{1,2}([\\-—～－]{1,2}[0-9]{4}\\.[0-9]{1,2})?)", content);
        if(StringUtils.isBlank(_times)){
            return 1;
            //throw new OpException(realname + "第{0}行{1}简历读取时间为空", r.row==null?r.fRow:r.row, r.row==null?"其间":"");
        }
        List<String> times = PatternUtils.withdrawAll("([0-9]{4}\\.[0-9]{1,2})", _times);
        if (times.size() >= 2) {
            r.start = DateUtils.parseStringToDate(times.get(0));
            r.end = DateUtils.parseStringToDate(times.get(1));
        } else if (times.size() == 1) {
            r.start = DateUtils.parseStringToDate(times.get(0));
        }

        if(r.start==null){
            return 1;
            //throw new OpException(realname + "第{0}行{1}简历读取起始时间为空", r.row==null?r.fRow:r.row, r.row==null?"其间":"");
        }

        String desc = PatternUtils.withdraw("[0-9]{4}\\.[0-9]{1,2}([\\-—～－]{1,2}[0-9]{4}\\.[0-9]{1,2})?\\s*(.*)",
                content, 2);
        desc = desc.replaceAll("[——|——现在]","");
        Pattern c = Pattern.compile("至今");
        Matcher mc=c.matcher(desc);
        if (mc.find()){
            //System.out.println(mc.end());
            desc = desc.substring(mc.end());
        }

        if(StringUtils.isBlank(desc)){
            desc = "";
            r.isEdu = false;
            //throw new OpException(realname + "第{0}行{1}简历读取为空", r.row==null?r.fRow:r.row, r.row==null?"其间":"");
        }else {
            r.desc = desc.trim().replaceAll("(；|。|;)*", "");
            r.desc = desc.trim().replaceAll("[\\-—～－]{1,2}", "");

            // 判断是否是学习经历
            r.isEdu = ((StringUtils.containsAny(desc, "初中", "中学", "高中", "学习", "进修", "中专", "大专", "专科", "学士", "本硕连读", "直硕", "博士", "硕博连读", "本硕博连读", "直博")
                    || r.desc.endsWith("学生") || r.desc.endsWith("本科") || r.desc.endsWith("本科生") || r.desc.endsWith("研究生") || r.desc.endsWith("读大学"))
                    && !StringUtils.containsAny(desc, "工作", "支教", "助教", "讲师", "教师", "校长"))
                    || (StringUtils.contains(desc, "学位") && !StringUtils.containsAny(desc, "学位委员", "学位办公室"))
                    || (StringUtils.contains(desc, "毕业") && !StringUtils.contains(desc, "毕业生就业"))
                    || (StringUtils.contains(desc, "硕士") && !StringUtils.containsAny(desc, "硕士教育中心", "硕士办公室"));
        }

        // 博士后算工作经历
        if(r.isEdu && StringUtils.contains(desc, "博士后")){
           r.isEdu = false;
        }

        //System.out.println("desc = " + desc + "      "  + r.isEdu);
        return 0;
    }

    //解析简历（word2007版本）
    public static List<ResumeRow> parseDocxResume(String resume, String realname) {

        int row = 1;
        Map<Integer, Integer> resumeMap = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("(([\\(|（][其|期]间[:|：]?)?[1|2]\\d{3}\\.[0-9]{1,2}[\\-—～－]{1,2})+");
        Matcher matcher = pattern.matcher(resume);

        Integer _start = null;
        Integer _end = null;
        Integer _mid = null;
        while (matcher.find()) {
            //String _year = matcher.group(1);
            //System.out.println("_year = " + _year);
            _start = matcher.start(1);
            _end = _start;
            if (_mid != null){
                resumeMap.put(_mid, _end);
            }
            resumeMap.put(_start, _end);
            _mid = _start;

        }

        List<ResumeRow> resumeRows = new ArrayList<>();
        List<String> lineList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : resumeMap.entrySet()){

            String line = StringUtils.substring(resume, entry.getKey(), entry.getValue()==entry.getKey()?resume.length():entry.getValue());
            if ((getCount("\\(|（", line) != getCount("\\)|）", line)) || PatternUtils.match(".*([其|期]间).*", line)){
                int lastIdx = lineList.size()-1;
                lineList.set(lastIdx, StringUtils.trimToEmpty(lineList.get(lastIdx))+StringUtils.trimToEmpty(line));
            }else{
                lineList.add(line);
            }
        }

        for (String line : lineList) {

            // 读取每行经历
            line = line.trim();
            ResumeRow newRow = new ResumeRow();
            String subLine = null;
            /*if(PatternUtils.match("\\s*[（|\\(].*", line)){ // 如果是换行的其间经历
                newRow.fRow = row-1;
                newRow.isEduWork = resumeRows.get(resumeRows.size()-1).isEdu;
            }else{*/
            newRow.row = row++;

            // 提取其间经历
            subLine = PatternUtils.withdraw("([（|\\(]([其|期]间[：|:]?)?[0-9]{4}\\.[0-9]{1,2}[\\-—～－]{1,2}.+[）|\\)])[;|；|。|\\s]?", line);
            if (StringUtils.isNotBlank(subLine)) {
                line = line.replace(subLine, "");
            }
            //}

            parseResumeRow(newRow, line, realname);
            resumeRows.add(newRow);

            // 处理其间经历
            if (StringUtils.isNotBlank(subLine)) {
                subLine = subLine.trim();

                subLine = PatternUtils.withdraw("[（|\\(](.*[0-9]{4}(\\.[0-9]{1,2})?([\\-—～－]{1,2})?.*)[）|\\)]", subLine);
                subLine = subLine.replaceAll("[)|）][(|（]", "；");
                String[] subLines = subLine.split("；");
                for (String sub : subLines) {

                    //sub = sub.trim().replace("[其|期]间[：|:]", "");
                    sub = RegExUtils.removePattern(sub.trim(), "[其|期]间[：|:]?");
                    ResumeRow r = new ResumeRow();
                    r.fRow = newRow.row;
                    r.isEduWork = newRow.isEdu;
                    if(parseResumeRow(r, sub, realname) == 1){
                        newRow.note = sub;
                        continue;
                    }
                    resumeRows.add(r);
                }
            }
        }

        return resumeRows;
    }

    public static int getCount(String reg, String data){

        int count = 0;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()){
            count++;
        }

        return count;
    }
}
