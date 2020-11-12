package domain.pm;

import domain.member.MemberView;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import service.pm.Pm3MeetingService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pm3Meeting implements Serializable {

    public List<MemberView> getAbsentList(){
        Pm3MeetingService pm3MeetingService = CmTag.getBean(Pm3MeetingService.class);
        return pm3MeetingService.getMemberList(absents);
    }

    public SysUserView getPresenterUser(){
        return CmTag.getUserById(presenter);
    }

    public SysUserView getRecorderUser(){
        return CmTag.getUserById(recorder);
    }

    public Party getParty(){
        return CmTag.getParty(partyId);
    }

    public Branch getBranch(){
        return CmTag.getBranch(branchId);
    }

    private Integer id;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private Integer year;

    private Byte quarter;

    private Integer month;

    private String address;

    private Integer presenter;

    private Integer recorder;

    private String absents;

    private String absentReason;

    private String invitee;

    private Integer dueNum;

    private Integer attendNum;

    private Integer absentNum;

    private String content;

    private Byte status;

    private Boolean isBack;

    private String checkOpinion;

    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getAbsents() {
        return absents;
    }

    public void setAbsents(String absents) {
        this.absents = absents == null ? null : absents.trim();
    }

    public String getAbsentReason() {
        return absentReason;
    }

    public void setAbsentReason(String absentReason) {
        this.absentReason = absentReason == null ? null : absentReason.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion == null ? null : checkOpinion.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}