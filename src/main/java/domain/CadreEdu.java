package domain;

import java.io.Serializable;
import java.util.Date;

public class CadreEdu implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer eduId;

    private Boolean isHighEdu;

    private String school;

    private String dep;

    private String major;

    private Byte schoolType;

    private Date enrolTime;

    private Date finishTime;

    private Byte schoolLen;

    private Integer learnStyle;

    private Boolean hasDegree;

    private String degree;

    private Boolean isHighDegree;

    private String degreeCountry;

    private String degreeUnit;

    private Date degreeTime;

    private String remark;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public Boolean getIsHighEdu() {
        return isHighEdu;
    }

    public void setIsHighEdu(Boolean isHighEdu) {
        this.isHighEdu = isHighEdu;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep == null ? null : dep.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Byte getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Byte schoolType) {
        this.schoolType = schoolType;
    }

    public Date getEnrolTime() {
        return enrolTime;
    }

    public void setEnrolTime(Date enrolTime) {
        this.enrolTime = enrolTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Byte getSchoolLen() {
        return schoolLen;
    }

    public void setSchoolLen(Byte schoolLen) {
        this.schoolLen = schoolLen;
    }

    public Integer getLearnStyle() {
        return learnStyle;
    }

    public void setLearnStyle(Integer learnStyle) {
        this.learnStyle = learnStyle;
    }

    public Boolean getHasDegree() {
        return hasDegree;
    }

    public void setHasDegree(Boolean hasDegree) {
        this.hasDegree = hasDegree;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Boolean getIsHighDegree() {
        return isHighDegree;
    }

    public void setIsHighDegree(Boolean isHighDegree) {
        this.isHighDegree = isHighDegree;
    }

    public String getDegreeCountry() {
        return degreeCountry;
    }

    public void setDegreeCountry(String degreeCountry) {
        this.degreeCountry = degreeCountry == null ? null : degreeCountry.trim();
    }

    public String getDegreeUnit() {
        return degreeUnit;
    }

    public void setDegreeUnit(String degreeUnit) {
        this.degreeUnit = degreeUnit == null ? null : degreeUnit.trim();
    }

    public Date getDegreeTime() {
        return degreeTime;
    }

    public void setDegreeTime(Date degreeTime) {
        this.degreeTime = degreeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}