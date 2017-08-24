package domain.crs;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CrsApplicant implements Serializable {

    public CadreView getCadre(){
        return CmTag.getCadreByUserId(userId);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public CrsPost getPost(){
        return CmTag.getCrsPost(postId);
    }

    private Integer id;

    private Integer userId;

    private Integer postId;

    private Date enrollTime;

    private Boolean isQuit;

    private Boolean isRecommend;

    private String recommendOw;

    private String recommendCadre;

    private String recommendCrowd;

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
}