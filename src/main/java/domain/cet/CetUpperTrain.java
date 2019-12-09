package domain.cet;

import domain.sys.SysUserView;
import domain.unit.Unit;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetUpperTrain implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public SysUserView getAddUser(){
        return CmTag.getUserById(addUserId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }

    private Integer id;

    private Integer year;

    private Byte upperType;

    private Boolean type;

    private Integer unitId;

    private Integer userId;

    private Integer upperTrainTypeId;

    private String title;

    private Integer postId;

    private Integer organizer;

    private String otherOrganizer;

    private Integer trainType;

    private String trainName;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date startDate;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date endDate;

    private BigDecimal period;

    private String address;

    private String wordNote;

    private String pdfNote;

    private Byte addType;

    private Integer addUserId;

    private Date addTime;

    private Boolean isValid;

    private Boolean isDeleted;

    private String remark;

    private Byte status;

    private String backReason;

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

    public Byte getUpperType() {
        return upperType;
    }

    public void setUpperType(Byte upperType) {
        this.upperType = upperType;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUpperTrainTypeId() {
        return upperTrainTypeId;
    }

    public void setUpperTrainTypeId(Integer upperTrainTypeId) {
        this.upperTrainTypeId = upperTrainTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Integer organizer) {
        this.organizer = organizer;
    }

    public String getOtherOrganizer() {
        return otherOrganizer;
    }

    public void setOtherOrganizer(String otherOrganizer) {
        this.otherOrganizer = otherOrganizer == null ? null : otherOrganizer.trim();
    }

    public Integer getTrainType() {
        return trainType;
    }

    public void setTrainType(Integer trainType) {
        this.trainType = trainType;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName == null ? null : trainName.trim();
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

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWordNote() {
        return wordNote;
    }

    public void setWordNote(String wordNote) {
        this.wordNote = wordNote == null ? null : wordNote.trim();
    }

    public String getPdfNote() {
        return pdfNote;
    }

    public void setPdfNote(String pdfNote) {
        this.pdfNote = pdfNote == null ? null : pdfNote.trim();
    }

    public Byte getAddType() {
        return addType;
    }

    public void setAddType(Byte addType) {
        this.addType = addType;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason == null ? null : backReason.trim();
    }
}