package domain.cadre;

import persistence.dispatch.common.DispatchCadreRelateBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.dispatch.DispatchCadreRelate;
import sys.constants.DispatchConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.List;

public class CadrePost implements Serializable {

    public DispatchCadreRelateBean getDispatchCadreRelateBean(){
        List<DispatchCadreRelate> all = CmTag.findDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
        if(all==null) return null;
        return new DispatchCadreRelateBean(all);
    }

    @JsonIgnore
    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Integer cadreId;

    private Integer unitPostId;

    private String post;

    private Integer postType;

    private Integer adminLevel;

    private Boolean isCpc;

    private Integer postClassId;

    private Integer unitId;

    private Boolean isDouble;

    private String doubleUnitIds;

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

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
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

    public String getDoubleUnitIds() {
        return doubleUnitIds;
    }

    public void setDoubleUnitIds(String doubleUnitIds) {
        this.doubleUnitIds = doubleUnitIds == null ? null : doubleUnitIds.trim();
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