package domain.pm;

import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PmMeeting2 implements Serializable {
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

    private Integer id;

    private Integer partyId;

    private Integer branchId;

    private Integer year;

    private Byte quarter;

    private Integer month;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private String address;

    private Byte type1;

    private Byte type2;

    private Integer number1;

    private Integer number2;

    private String time1;

    private String time2;

    private String shortContent;

    private String content;

    private Integer dueNum;

    private Integer attendNum;

    private String absents;

    private Integer presenter;

    private Integer recorder;

    private String fileName;

    private String filePath;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Byte getType1() {
        return type1;
    }

    public void setType1(Byte type1) {
        this.type1 = type1;
    }

    public Byte getType2() {
        return type2;
    }

    public void setType2(Byte type2) {
        this.type2 = type2;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1 == null ? null : time1.trim();
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2 == null ? null : time2.trim();
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent == null ? null : shortContent.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getAbsents() {
        return absents;
    }

    public void setAbsents(String absents) {
        this.absents = absents == null ? null : absents.trim();
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
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