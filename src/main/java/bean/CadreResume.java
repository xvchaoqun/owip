package bean;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2018/5/18.
 * 简历
 */
public class CadreResume {

    public static byte TYPE_WORK = 1;
    public static byte TYPE_EDU = 2;
    public static byte TYPE_EDU_DOUBLE = 3;

    private byte type; // 1:工作经历 2: 学习经历 3: 学习经历(双学位)
    private Date startDate;     // yyyy.MM
    private Date endDate;       // yyyy.MM
    private String detail;      // 描述
    private List<CadreResume> containResumes;  // 其间（包含）
    private List<CadreResume> overlapResumes;  // 其间（不包含）

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<CadreResume> getContainResumes() {
        return containResumes;
    }

    public void setContainResumes(List<CadreResume> containResumes) {
        this.containResumes = containResumes;
    }

    public List<CadreResume> getOverlapResumes() {
        return overlapResumes;
    }

    public void setOverlapResumes(List<CadreResume> overlapResumes) {
        this.overlapResumes = overlapResumes;
    }
}
