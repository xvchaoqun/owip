package bean;

import sys.utils.DateUtils;

import java.util.Date;

// 中组部简历部分的每行属性
public class ResumeRow {

    // 行号（不计算其间经历）
    public Integer row;
    // 起始时间
    public Date start;
    // 结束时间（可能为空）
    public Date end;
    // 文字描述部分
    public String desc;
    //工作经历的补充说明
    public String note;
    // 是否学习工作经历，否则工作经历
    public boolean isEdu;
    // 所属主要工作经历或学习经历的行号
    public Integer fRow;
    // 是否学习经历的其间经历
    public boolean isEduWork;

    @Override
    public String toString() {
        return "Resume{" +
                "row=" + row +
                ", start=" + DateUtils.formatDate(start, DateUtils.YYYYMM) +
                ", end=" + DateUtils.formatDate(end, DateUtils.YYYYMM) +
                ", parseResumeRow='" + desc + '\'' +
                ", isEdu=" + isEdu +
                ", fRow=" + fRow +
                ", isEduWork=" + isEduWork +
                '}';
    }
}