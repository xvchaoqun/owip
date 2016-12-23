package domain.cadre;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sys.MetaType;
import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Cadre implements Serializable {
    public SysUserView getUser(){

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
    // 离任文件
    public Dispatch getDispatch(){
        if(dispatchCadreId!=null){
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            if(dispatchCadre!=null)
                return CmTag.getDispatch(dispatchCadre.getDispatchId());
        }
        return null;
    }
    private Integer id;

    private Integer userId;

    private Integer typeId;

    private Integer postId;

    private Integer unitId;

    private String title;

    private Integer dispatchCadreId;

    private String post;

    private Integer dpTypeId;

    private Date dpAddTime;

    private String dpPost;

    private String dpRemark;

    private Boolean isDp;

    private String remark;

    private Integer sortOrder;

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

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getDpTypeId() {
        return dpTypeId;
    }

    public void setDpTypeId(Integer dpTypeId) {
        this.dpTypeId = dpTypeId;
    }

    public Date getDpAddTime() {
        return dpAddTime;
    }

    public void setDpAddTime(Date dpAddTime) {
        this.dpAddTime = dpAddTime;
    }

    public String getDpPost() {
        return dpPost;
    }

    public void setDpPost(String dpPost) {
        this.dpPost = dpPost == null ? null : dpPost.trim();
    }

    public String getDpRemark() {
        return dpRemark;
    }

    public void setDpRemark(String dpRemark) {
        this.dpRemark = dpRemark == null ? null : dpRemark.trim();
    }

    public Boolean getIsDp() {
        return isDp;
    }

    public void setIsDp(Boolean isDp) {
        this.isDp = isDp;
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
}