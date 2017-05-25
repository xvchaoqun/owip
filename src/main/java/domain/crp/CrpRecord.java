package domain.crp;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CrpRecord implements Serializable {

    public CadreView getCadre(){

        return (userId==null)?null: CmTag.getCadreByUserId(userId);
    }

    public SysUserView getUser(){

        return (userId==null)?null: CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private String realname;

    private Boolean isPresentCadre;

    private String presentPost;

    private String phone;

    private Integer toUnitType;

    private String toUnit;

    private Integer tempPostType;

    private String tempPost;

    private String project;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date endDate;

    private Boolean isFinished;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date realEndDate;

    private Byte type;

    private Boolean isDeleted;

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Boolean getIsPresentCadre() {
        return isPresentCadre;
    }

    public void setIsPresentCadre(Boolean isPresentCadre) {
        this.isPresentCadre = isPresentCadre;
    }

    public String getPresentPost() {
        return presentPost;
    }

    public void setPresentPost(String presentPost) {
        this.presentPost = presentPost == null ? null : presentPost.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getToUnitType() {
        return toUnitType;
    }

    public void setToUnitType(Integer toUnitType) {
        this.toUnitType = toUnitType;
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit == null ? null : toUnit.trim();
    }

    public Integer getTempPostType() {
        return tempPostType;
    }

    public void setTempPostType(Integer tempPostType) {
        this.tempPostType = tempPostType;
    }

    public String getTempPost() {
        return tempPost;
    }

    public void setTempPost(String tempPost) {
        this.tempPost = tempPost == null ? null : tempPost.trim();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Date getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(Date realEndDate) {
        this.realEndDate = realEndDate;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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
}