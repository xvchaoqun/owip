package domain.crs;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.util.Date;

public class CrsApplicantStatView implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer postId;

    private String report;

    private String pptName;

    @SignRes
    private String ppt;

    private Date enrollTime;

    private Boolean isQuit;

    private Boolean isRecommend;

    private String recommendOw;

    private String recommendCadre;

    private String recommendCrowd;

    @SignRes
    private String recommendPdf;

    private Byte infoCheckStatus;

    private String infoCheckRemark;

    private Byte requireCheckStatus;

    private String requireCheckRemark;

    private Boolean specialStatus;

    private String specialPdf;

    private String specialRemark;

    private Integer recommendFirstCount;

    private Integer recommendSecondCount;

    private Byte status;

    private Boolean isRequireCheckPass;

    private String realname;

    private Boolean isFirst;

    private Long expertCount;

    private Long applicantCount;

    private Byte crsPostType;

    private Integer crsPostYear;

    private Integer crsPostSeq;

    private String crsPostName;

    private String crsPostJob;

    private Byte crsPostStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName == null ? null : pptName.trim();
    }

    public String getPpt() {
        return ppt;
    }

    public void setPpt(String ppt) {
        this.ppt = ppt == null ? null : ppt.trim();
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getRecommendOw() {
        return recommendOw;
    }

    public void setRecommendOw(String recommendOw) {
        this.recommendOw = recommendOw == null ? null : recommendOw.trim();
    }

    public String getRecommendCadre() {
        return recommendCadre;
    }

    public void setRecommendCadre(String recommendCadre) {
        this.recommendCadre = recommendCadre == null ? null : recommendCadre.trim();
    }

    public String getRecommendCrowd() {
        return recommendCrowd;
    }

    public void setRecommendCrowd(String recommendCrowd) {
        this.recommendCrowd = recommendCrowd == null ? null : recommendCrowd.trim();
    }

    public String getRecommendPdf() {
        return recommendPdf;
    }

    public void setRecommendPdf(String recommendPdf) {
        this.recommendPdf = recommendPdf == null ? null : recommendPdf.trim();
    }

    public Byte getInfoCheckStatus() {
        return infoCheckStatus;
    }

    public void setInfoCheckStatus(Byte infoCheckStatus) {
        this.infoCheckStatus = infoCheckStatus;
    }

    public String getInfoCheckRemark() {
        return infoCheckRemark;
    }

    public void setInfoCheckRemark(String infoCheckRemark) {
        this.infoCheckRemark = infoCheckRemark == null ? null : infoCheckRemark.trim();
    }

    public Byte getRequireCheckStatus() {
        return requireCheckStatus;
    }

    public void setRequireCheckStatus(Byte requireCheckStatus) {
        this.requireCheckStatus = requireCheckStatus;
    }

    public String getRequireCheckRemark() {
        return requireCheckRemark;
    }

    public void setRequireCheckRemark(String requireCheckRemark) {
        this.requireCheckRemark = requireCheckRemark == null ? null : requireCheckRemark.trim();
    }

    public Boolean getSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(Boolean specialStatus) {
        this.specialStatus = specialStatus;
    }

    public String getSpecialPdf() {
        return specialPdf;
    }

    public void setSpecialPdf(String specialPdf) {
        this.specialPdf = specialPdf == null ? null : specialPdf.trim();
    }

    public String getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(String specialRemark) {
        this.specialRemark = specialRemark == null ? null : specialRemark.trim();
    }

    public Integer getRecommendFirstCount() {
        return recommendFirstCount;
    }

    public void setRecommendFirstCount(Integer recommendFirstCount) {
        this.recommendFirstCount = recommendFirstCount;
    }

    public Integer getRecommendSecondCount() {
        return recommendSecondCount;
    }

    public void setRecommendSecondCount(Integer recommendSecondCount) {
        this.recommendSecondCount = recommendSecondCount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsRequireCheckPass() {
        return isRequireCheckPass;
    }

    public void setIsRequireCheckPass(Boolean isRequireCheckPass) {
        this.isRequireCheckPass = isRequireCheckPass;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }

    public Long getExpertCount() {
        return expertCount;
    }

    public void setExpertCount(Long expertCount) {
        this.expertCount = expertCount;
    }

    public Long getApplicantCount() {
        return applicantCount;
    }

    public void setApplicantCount(Long applicantCount) {
        this.applicantCount = applicantCount;
    }

    public Byte getCrsPostType() {
        return crsPostType;
    }

    public void setCrsPostType(Byte crsPostType) {
        this.crsPostType = crsPostType;
    }

    public Integer getCrsPostYear() {
        return crsPostYear;
    }

    public void setCrsPostYear(Integer crsPostYear) {
        this.crsPostYear = crsPostYear;
    }

    public Integer getCrsPostSeq() {
        return crsPostSeq;
    }

    public void setCrsPostSeq(Integer crsPostSeq) {
        this.crsPostSeq = crsPostSeq;
    }

    public String getCrsPostName() {
        return crsPostName;
    }

    public void setCrsPostName(String crsPostName) {
        this.crsPostName = crsPostName == null ? null : crsPostName.trim();
    }

    public String getCrsPostJob() {
        return crsPostJob;
    }

    public void setCrsPostJob(String crsPostJob) {
        this.crsPostJob = crsPostJob == null ? null : crsPostJob.trim();
    }

    public Byte getCrsPostStatus() {
        return crsPostStatus;
    }

    public void setCrsPostStatus(Byte crsPostStatus) {
        this.crsPostStatus = crsPostStatus;
    }
}