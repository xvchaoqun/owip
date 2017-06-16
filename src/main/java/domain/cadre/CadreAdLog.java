package domain.cadre;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.base.MetaType;
import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CadreAdLog implements Serializable {

/*    public Cadre getCadre() {
        return CmTag.getCadreById(cadreId);
    }*/

    public SysUserView getOpUser() {
        return CmTag.getUserById(userId);
    }

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

    private Integer cadreId;

    private Integer typeId;

    private Integer postId;

    private Integer unitId;

    private String title;

    private Integer dispatchCadreId;

    private String post;

    private String remark;

    private Byte status;

    private Byte module;

    private Integer moduleId;

    private Integer userId;

    private String content;

    private String ip;

    private Date createTime;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getModule() {
        return module;
    }

    public void setModule(Byte module) {
        this.module = module;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}