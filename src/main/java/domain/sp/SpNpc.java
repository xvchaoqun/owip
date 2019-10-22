package domain.sp;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class SpNpc implements Serializable {

    public SysUserView getUser(){ return CmTag.getUserById(userId); }

    public CadreView getCadre(){ return CmTag.getCadreByUserId(userId);}

    private Integer id;

    private Integer type;

    private String th;

    private Integer userId;

    private Integer politicsStatus;

    private String npcPost;

    private Integer unitId;

    private String electedPost;

    private String post;

    private Boolean isCadre;

    private Boolean isHistory;

    private String phone;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th == null ? null : th.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(Integer politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getNpcPost() {
        return npcPost;
    }

    public void setNpcPost(String npcPost) {
        this.npcPost = npcPost == null ? null : npcPost.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getElectedPost() {
        return electedPost;
    }

    public void setElectedPost(String electedPost) {
        this.electedPost = electedPost == null ? null : electedPost.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean isCadre) {
        this.isCadre = isCadre;
    }

    public Boolean getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Boolean isHistory) {
        this.isHistory = isHistory;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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