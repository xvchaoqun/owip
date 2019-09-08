package domain.crs;

import domain.sc.scRecord.ScRecordView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import domain.unit.UnitPost;
import persistence.sc.IScMapper;
import persistence.unit.UnitPostMapper;
import service.crs.CrsApplicantService;
import sys.constants.CrsConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.*;

public class CrsPost implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}
    public ScRecordView getScRecord(){
        if(recordId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        if(iScMapper==null) return null;
        return iScMapper.getScRecordView(recordId);
    }

    public List<Map<String, Object>> getApplicants(){

        CrsApplicantService crsApplicantService = CmTag.getBean(CrsApplicantService.class);
        List<CrsApplicantView> crsApplicants = crsApplicantService.getCrsApplicants(id);

        List<Map<String, Object>> applicants = new ArrayList<>();
        for (CrsApplicantView crsApplicant : crsApplicants) {

            Map<String, Object> applicant = new HashMap<>();
            applicant.put("userId", crsApplicant.getUserId());
            applicant.put("realname", crsApplicant.getUser().getRealname());
            applicant.put("requireCheckStatus", crsApplicant.getRequireCheckStatus());
            applicant.put("isRequireCheckPass", crsApplicant.getIsRequireCheckPass());
            applicants.add(applicant);
        }

        return applicants;
    }

    public Unit getUnit() {
        return CmTag.getUnit(unitId);
    }

    public Boolean getAutoSwitch(){
        return enrollStatus == CrsConstants.CRS_POST_ENROLL_STATUS_DEFAULT;
    }

    public Byte getSwitchStatus() {

        // 手动开关判断
        if (enrollStatus != CrsConstants.CRS_POST_ENROLL_STATUS_DEFAULT) {
            return enrollStatus;
        }

        // 自动开关判断
        Date now = new Date();
        if (startTime != null && endTime != null) {

            if (now.after(startTime) && now.before(endTime)) {
                return CrsConstants.CRS_POST_ENROLL_STATUS_OPEN;
            }
        } else if (startTime != null) {

            if (now.after(startTime)) {
                return CrsConstants.CRS_POST_ENROLL_STATUS_OPEN;
            }
        } else if (endTime != null) {

            if (now.before(endTime)) {
                return CrsConstants.CRS_POST_ENROLL_STATUS_OPEN;
            }
        }

        return CrsConstants.CRS_POST_ENROLL_STATUS_CLOSED;
    }

    public UnitPost getUnitPost(){
        if(unitPostId==null) return null;
        UnitPostMapper unitPostMapper = CmTag.getBean(UnitPostMapper.class);
        return unitPostMapper.selectByPrimaryKey(unitPostId);
    }

    private Integer id;

    private Integer year;

    private Byte type;

    private Integer seq;

    private Integer recordId;

    private Integer recordUserId;

    private Integer unitPostId;

    private String name;

    private String job;

    private Integer adminLevel;

    private Integer unitId;

    private Integer num;

    private String notice;

    private Integer postRequireId;

    private Date startTime;

    private Date endTime;

    private Byte enrollStatus;

    private Integer meetingApplyCount;

    private Date meetingTime;

    private String meetingAddress;

    private Date reportDeadline;

    private Date quitDeadline;

    private Date pptDeadline;

    private Boolean pptUploadClosed;

    private Boolean meetingStatus;

    private Boolean committeeStatus;

    private String remark;

    private Date createTime;

    private Byte pubStatus;

    private Byte status;

    private Integer statGiveCount;

    private Integer statBackCount;

    private String statFile;

    private String statFileName;

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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
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

    public Integer getMeetingApplyCount() {
        return meetingApplyCount;
    }

    public void setMeetingApplyCount(Integer meetingApplyCount) {
        this.meetingApplyCount = meetingApplyCount;
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

    public Date getPptDeadline() {
        return pptDeadline;
    }

    public void setPptDeadline(Date pptDeadline) {
        this.pptDeadline = pptDeadline;
    }

    public Boolean getPptUploadClosed() {
        return pptUploadClosed;
    }

    public void setPptUploadClosed(Boolean pptUploadClosed) {
        this.pptUploadClosed = pptUploadClosed;
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

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }
}