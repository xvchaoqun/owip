package domain.member;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private Integer userId;

    private String code;

    private String realname;

    private Byte gender;

    private Date birth;

    private String nation;

    private String nativePlace;

    private String idcard;

    private String type;

    private String eduLevel;

    private String eduType;

    private String eduCategory;

    private String eduWay;

    private Boolean isFullTime;

    private String enrolYear;

    private String period;

    private String grade;

    private Date actualEnrolTime;

    private Date expectGraduateTime;

    private Float delayYear;

    private Date actualGraduateTime;

    private String xjStatus;

    private Byte syncSource;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel == null ? null : eduLevel.trim();
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType == null ? null : eduType.trim();
    }

    public String getEduCategory() {
        return eduCategory;
    }

    public void setEduCategory(String eduCategory) {
        this.eduCategory = eduCategory == null ? null : eduCategory.trim();
    }

    public String getEduWay() {
        return eduWay;
    }

    public void setEduWay(String eduWay) {
        this.eduWay = eduWay == null ? null : eduWay.trim();
    }

    public Boolean getIsFullTime() {
        return isFullTime;
    }

    public void setIsFullTime(Boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public String getEnrolYear() {
        return enrolYear;
    }

    public void setEnrolYear(String enrolYear) {
        this.enrolYear = enrolYear == null ? null : enrolYear.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public Date getActualEnrolTime() {
        return actualEnrolTime;
    }

    public void setActualEnrolTime(Date actualEnrolTime) {
        this.actualEnrolTime = actualEnrolTime;
    }

    public Date getExpectGraduateTime() {
        return expectGraduateTime;
    }

    public void setExpectGraduateTime(Date expectGraduateTime) {
        this.expectGraduateTime = expectGraduateTime;
    }

    public Float getDelayYear() {
        return delayYear;
    }

    public void setDelayYear(Float delayYear) {
        this.delayYear = delayYear;
    }

    public Date getActualGraduateTime() {
        return actualGraduateTime;
    }

    public void setActualGraduateTime(Date actualGraduateTime) {
        this.actualGraduateTime = actualGraduateTime;
    }

    public String getXjStatus() {
        return xjStatus;
    }

    public void setXjStatus(String xjStatus) {
        this.xjStatus = xjStatus == null ? null : xjStatus.trim();
    }

    public Byte getSyncSource() {
        return syncSource;
    }

    public void setSyncSource(Byte syncSource) {
        this.syncSource = syncSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}