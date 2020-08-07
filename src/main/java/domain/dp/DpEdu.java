package domain.dp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import sys.spring.UserResUtils;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DpEdu implements Serializable {

    public String getSignCertificates(){

        if(StringUtils.isNotBlank(certificate)){
            List<String> signCertificates = new ArrayList<>();
            List<String> certificates = Arrays.asList(certificate.split(","));
            for (String cert : certificates) {
                signCertificates.add(UserResUtils.sign(cert));
            }
            return StringUtils.join(signCertificates, ",");
        }

        return null;
    }

    private List<DpWork> subDpWorks;

    public List<DpWork> getSubDpWorks() {
        return subDpWorks;
    }

    public void setSubDpWorks(List<DpWork> subDpWorks) {
        this.subDpWorks = subDpWorks;
    }

    private Integer id;

    private Integer subWorkCount;

    private Integer userId;

    private Integer eduId;

    private Boolean isGraduated;

    private Boolean isHighEdu;

    private String school;

    private String dep;

    private Integer subject;

    private Integer firstSubject;

    private String major;

    private Byte schoolType;

    @DateTimeFormat(pattern = DateUtils.YYYYMM)
    private Date enrolTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMM)
    private Date finishTime;

    private Integer learnStyle;

    private Boolean hasDegree;

    private Byte degreeType;

    private String degree;

    private Boolean isHighDegree;

    private Boolean isSecondDegree;

    private String degreeCountry;

    private String degreeUnit;

    @DateTimeFormat(pattern = DateUtils.YYYYMM)
    private Date degreeTime;

    private String tutorName;

    private String tutorTitle;

    private String certificate;

    private String remark;

    private String note;

    private Integer sortOrder;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubWorkCount() {
        return subWorkCount;
    }

    public void setSubWorkCount(Integer subWorkCount) {
        this.subWorkCount = subWorkCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public Boolean getIsGraduated() {
        return isGraduated;
    }

    public void setIsGraduated(Boolean isGraduated) {
        this.isGraduated = isGraduated;
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

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getFirstSubject() {
        return firstSubject;
    }

    public void setFirstSubject(Integer firstSubject) {
        this.firstSubject = firstSubject;
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

    public Byte getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(Byte degreeType) {
        this.degreeType = degreeType;
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

    public Boolean getIsSecondDegree() {
        return isSecondDegree;
    }

    public void setIsSecondDegree(Boolean isSecondDegree) {
        this.isSecondDegree = isSecondDegree;
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

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName == null ? null : tutorName.trim();
    }

    public String getTutorTitle() {
        return tutorTitle;
    }

    public void setTutorTitle(String tutorTitle) {
        this.tutorTitle = tutorTitle == null ? null : tutorTitle.trim();
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate == null ? null : certificate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}