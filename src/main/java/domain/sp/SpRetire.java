package domain.sp;

import domain.sys.SysUserView;
import domain.sys.TeacherInfo;
import service.sys.TeacherInfoService;
import sys.tags.CmTag;

import java.io.Serializable;

public class SpRetire implements Serializable {

    public SysUserView getUser(){ return CmTag.getUserById(userId);}

    public TeacherInfo getTeacherInfo(){
        TeacherInfoService teacherInfoService = CmTag.getBean(TeacherInfoService.class);
        return teacherInfoService.get(userId);
    }

    private Integer id;

    private Integer userId;

    private Integer unitId;

    private Integer politicsStatus;

    private String staffStatus;

    private String onJob;

    private String postClass;

    private String proPost;

    private String manageLevel;

    private Boolean isCadre;

    private Integer sortOrder;

    private String remark;

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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(Integer politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus == null ? null : staffStatus.trim();
    }

    public String getOnJob() {
        return onJob;
    }

    public void setOnJob(String onJob) {
        this.onJob = onJob == null ? null : onJob.trim();
    }

    public String getPostClass() {
        return postClass;
    }

    public void setPostClass(String postClass) {
        this.postClass = postClass == null ? null : postClass.trim();
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel == null ? null : manageLevel.trim();
    }

    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean isCadre) {
        this.isCadre = isCadre;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}