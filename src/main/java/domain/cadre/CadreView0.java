package domain.cadre;

import domain.sys.MetaType;
import domain.sys.SysUser;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CadreView0 implements Serializable {

    public SysUser getUser(){

        return CmTag.getUserById(userId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }
    public MetaType getAdminLevelType(){
        Map<Integer, MetaType> adminLevelMap = CmTag.getMetaTypes("mc_admin_level");
        return adminLevelMap.get(typeId);
    }
    public MetaType getPostType(){
        Map<Integer, MetaType> postMap = CmTag.getMetaTypes("mc_post");
        return postMap.get(postId);
    }

    // 兼审单位
    public List<CadreAdditionalPost> getCadreAdditionalPosts(){

        return CmTag.getCadreAdditionalPosts(id);
    }

    private Integer id;

    private Integer userId;

    private Integer typeId;

    private Integer postId;

    private Integer unitId;

    private String title;

    private String post;

    private String remark;

    private Integer sortOrder;

    private Byte status;

    private String mobile;

    private String officePhone;

    private String homePhone;

    private String email;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone == null ? null : officePhone.trim();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}