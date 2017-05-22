package domain.cadre;

import bean.DispatchCadreRelateBean;
import domain.dispatch.DispatchCadreRelate;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.List;

public class CadrePost implements Serializable {

    public DispatchCadreRelateBean getDispatchCadreRelateBean(){
        List<DispatchCadreRelate> all = CmTag.findDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
        return new DispatchCadreRelateBean(all);
    }

    private Integer id;

    private Integer cadreId;

    private String post;

    private Integer postId;

    private Integer adminLevelId;

    private Boolean isCpc;

    private Integer postClassId;

    private Integer unitId;

    private Boolean isDouble;

    private Integer doubleUnitId;

    private Boolean isMainPost;

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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getAdminLevelId() {
        return adminLevelId;
    }

    public void setAdminLevelId(Integer adminLevelId) {
        this.adminLevelId = adminLevelId;
    }

    public Boolean getIsCpc() {
        return isCpc;
    }

    public void setIsCpc(Boolean isCpc) {
        this.isCpc = isCpc;
    }

    public Integer getPostClassId() {
        return postClassId;
    }

    public void setPostClassId(Integer postClassId) {
        this.postClassId = postClassId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Boolean getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Boolean isDouble) {
        this.isDouble = isDouble;
    }

    public Integer getDoubleUnitId() {
        return doubleUnitId;
    }

    public void setDoubleUnitId(Integer doubleUnitId) {
        this.doubleUnitId = doubleUnitId;
    }

    public Boolean getIsMainPost() {
        return isMainPost;
    }

    public void setIsMainPost(Boolean isMainPost) {
        this.isMainPost = isMainPost;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}