package bean;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2018/5/18.
 * 简历
 */
public class CadreResume {

    private boolean isWork; // 工作经历/学习经历
    private Date startDate;     // yyyy.MM
    private Date endDate;       // yyyy.MM
    private String detail;      // 描述
    private List<CadreResume> resumes;  // 其间

    public boolean isWork() {
        return isWork;
    }

    public void setIsWork(boolean isWork) {
        this.isWork = isWork;
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

    public List<CadreResume> getResumes() {
        return resumes;
    }

    public void setResumes(List<CadreResume> resumes) {
        this.resumes = resumes;
    }
}
