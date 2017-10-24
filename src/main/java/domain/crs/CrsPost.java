package domain.crs;

import domain.unit.Unit;
import service.crs.CrsApplicantService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CrsPost implements Serializable {

    public int getApplicantCount(){

        CrsApplicantService crsApplicantService = CmTag.getBean(CrsApplicantService.class);
        List<CrsApplicantView> crsApplicants = crsApplicantService.getCrsApplicants(id);
        return crsApplicants.size();
    }

    public Unit getUnit() {
        return CmTag.getUnit(unitId);
    }

    public Boolean getAutoSwitch(){
        return enrollStatus == SystemConstants.CRS_POST_ENROLL_STATUS_DEFAULT;
    }

    public Byte getSwitchStatus() {

        // 手动开关判断
        if (enrollStatus != SystemConstants.CRS_POST_ENROLL_STATUS_DEFAULT) {
            return enrollStatus;
        }

        // 自动开关判断
        Date now = new Date();
        if (startTime != null && endTime != null) {

            if (now.after(startTime) && now.before(endTime)) {
                return SystemConstants.CRS_POST_ENROLL_STATUS_OPEN;
            }
        } else if (startTime != null) {

            if (now.after(startTime)) {
                return SystemConstants.CRS_POST_ENROLL_STATUS_OPEN;
            }
        } else if (endTime != null) {

            if (now.before(endTime)) {
                return SystemConstants.CRS_POST_ENROLL_STATUS_OPEN;
            }
        }

        return SystemConstants.CRS_POST_ENROLL_STATUS_CLOSED;
    }

    private Integer id;

    private Integer year;

    private Byte type;

    private Integer seq;

    private String name;

    private String job;

    private Integer adminLevel;

    private Integer unitId;

    private Integer num;

    private String notice;

    private String requirement;

    private String qualification;

    private Integer postRequireId;

    private Date startTime;

    private Date endTime;

    private Byte enrollStatus;

    private Date meetingTime;

    private String meetingAddress;

    private String meetingNotice;

    private Date reportDeadline;

    private Date quitDeadline;

    private Boolean meetingStatus;

    private Boolean committeeStatus;

    private String remark;

    private Date createTime;

    private Byte pubStatus;

    private Byte status;

    private String meetingSummary;

    private Integer statGiveCount;

    private Integer statBackCount;

    private String statFile;

    private String statFileName;

    private Integer statFirstUserId;

    private Integer statSecondUserId;

    private Date statDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification == null ? null : qualification.trim();
    }

    public Integer getPostRequireId() {
        return postRequireId;
    }

    public void setPostRequireId(Integer postRequireId) {
        this.postRequireId = postRequireId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(Byte enrollStatus) {
        this.enrollStatus = enrollStatus;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress == null ? null : meetingAddress.trim();
    }

    public String getMeetingNotice() {
        return meetingNotice;
    }

    public void setMeetingNotice(String meetingNotice) {
        this.meetingNotice = meetingNotice == null ? null : meetingNotice.trim();
    }

    public Date getReportDeadline() {
        return reportDeadline;
    }

    public void setReportDeadline(Date reportDeadline) {
        this.reportDeadline = reportDeadline;
    }

    public Date getQuitDeadline() {
        return quitDeadline;
    }

    public void setQuitDeadline(Date quitDeadline) {
        this.quitDeadline = quitDeadline;
    }

    public Boolean getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(Boolean meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public Boolean getCommitteeStatus() {
        return committeeStatus;
    }

    public void setCommitteeStatus(Boolean committeeStatus) {
        this.committeeStatus = committeeStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(Byte pubStatus) {
        this.pubStatus = pubStatus;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMeetingSummary() {
        return meetingSummary;
    }

    public void setMeetingSummary(String meetingSummary) {
        this.meetingSummary = meetingSummary == null ? null : meetingSummary.trim();
    }

    public Integer getStatGiveCount() {
        return statGiveCount;
    }

    public void setStatGiveCount(Integer statGiveCount) {
        this.statGiveCount = statGiveCount;
    }

    public Integer getStatBackCount() {
        return statBackCount;
    }

    public void setStatBackCount(Integer statBackCount) {
        this.statBackCount = statBackCount;
    }

    public String getStatFile() {
        return statFile;
    }

    public void setStatFile(String statFile) {
        this.statFile = statFile == null ? null : statFile.trim();
    }

    public String getStatFileName() {
        return statFileName;
    }

    public void setStatFileName(String statFileName) {
        this.statFileName = statFileName == null ? null : statFileName.trim();
    }

    public Integer getStatFirstUserId() {
        return statFirstUserId;
    }

    public void setStatFirstUserId(Integer statFirstUserId) {
        this.statFirstUserId = statFirstUserId;
    }

    public Integer getStatSecondUserId() {
        return statSecondUserId;
    }

    public void setStatSecondUserId(Integer statSecondUserId) {
        this.statSecondUserId = statSecondUserId;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }
}