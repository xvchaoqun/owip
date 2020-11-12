package domain.pm;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Pm3Guide implements Serializable {
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date meetingMonth;

    private String guideFiles;

    private String guideFilenames;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reportTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMeetingMonth() {
        return meetingMonth;
    }

    public void setMeetingMonth(Date meetingMonth) {
        this.meetingMonth = meetingMonth;
    }

    public String getGuideFiles() {
        return guideFiles;
    }

    public void setGuideFiles(String guideFiles) {
        this.guideFiles = guideFiles == null ? null : guideFiles.trim();
    }

    public String getGuideFilenames() {
        return guideFilenames;
    }

    public void setGuideFilenames(String guideFilenames) {
        this.guideFilenames = guideFilenames == null ? null : guideFilenames.trim();
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}