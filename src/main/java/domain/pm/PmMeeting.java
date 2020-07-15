package domain.pm;

import domain.member.MemberView;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.pm.PmMeetingFileMapper;
import service.pm.PmMeetingService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PmMeeting implements Serializable {
    public List<MemberView> getAttendList(){
        PmMeetingService pmMeetingService = CmTag.getBean(PmMeetingService.class);
        return pmMeetingService.getMemberList(attends);
    }
    public List<MemberView> getAbsentList(){
        PmMeetingService pmMeetingService = CmTag.getBean(PmMeetingService.class);
        return pmMeetingService.getMemberList(absents);
    }

    public Party getParty(){
        return CmTag.getParty(partyId);

    }
    public Branch getBranch(){
        return CmTag.getBranch(branchId);

    }

    public SysUserView getPresenterName(){
        return CmTag.getUserById(presenter);
    }

    public SysUserView getRecorderName(){
        return CmTag.getUserById(recorder);
    }

    public long getFileCount(){
        PmMeetingFileMapper pmMeetingFileMapper = CmTag.getBean(PmMeetingFileMapper.class);
        PmMeetingFileExample example = new PmMeetingFileExample();
        example.createCriteria().andMeetingIdEqualTo(id);
        return pmMeetingFileMapper.countByExample(example);
    }

    private Integer id;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date planDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private Integer year;

    private Byte quarter;

    private Integer month;

    private String name;

    private String issue;

    private String address;

    private Integer presenter;

    private Integer recorder;

    private String attends;

    private String absents;

    private String invitee;

    private Integer dueNum;

    private Integer attendNum;

    private Integer absentNum;

    private String absentReason;

    private String content;

    private String decision;

    private Boolean isPublic;

    private String remark;

    private Byte status;

    private Boolean isBack;

    private String reason;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getQuarter() {
        return quarter;
    }

    public void setQuarter(Byte quarter) {
        this.quarter = quarter;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue == null ? null : issue.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getPresenter() {
        return presenter;
    }

    public void setPresenter(Integer presenter) {
        this.presenter = presenter;
    }

    public Integer getRecorder() {
        return recorder;
    }

    public void setRecorder(Integer recorder) {
        this.recorder = recorder;
    }

    public String getAttends() {
        return attends;
    }

    public void setAttends(String attends) {
        this.attends = attends == null ? null : attends.trim();
    }

    public String getAbsents() {
        return absents;
    }

    public void setAbsents(String absents) {
        this.absents = absents == null ? null : absents.trim();
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee == null ? null : invitee.trim();
    }

    public Integer getDueNum() {
        return dueNum;
    }

    public void setDueNum(Integer dueNum) {
        this.dueNum = dueNum;
    }

    public Integer getAttendNum() {
        return attendNum;
    }

    public void setAttendNum(Integer attendNum) {
        this.attendNum = attendNum;
    }

    public Integer getAbsentNum() {
        return absentNum;
    }

    public void setAbsentNum(Integer absentNum) {
        this.absentNum = absentNum;
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason == null ? null : absentReason.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision == null ? null : decision.trim();
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}