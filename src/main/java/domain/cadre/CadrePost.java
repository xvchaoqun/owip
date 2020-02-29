package domain.cadre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadreRelate;
import persistence.dispatch.common.DispatchCadreRelateBean;
import sys.constants.DispatchConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CadrePost implements Serializable {

    @JsonIgnore
    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

     public DispatchCadreRelateBean getDispatchCadreRelateBean(){

        List<DispatchCadreRelate> all = CmTag.findDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_POST);
        if(all==null) return null;
        return new DispatchCadreRelateBean(all);
    }

    public Dispatch getNpDispatch(){
        return CmTag.getDispatch(npDispatchId);
    }

    public Dispatch getLpDispatch(){
        return CmTag.getDispatch(lpDispatchId);
    }

    private Integer id;

    private Integer cadreId;

    private Integer unitPostId;

    private String postName;

    private String post;

    private Integer lpDispatchId;

    private Date lpWorkTime;

    private Integer npDispatchId;

    private Date npWorkTime;

    private Integer postType;

    private Integer adminLevel;

    private Boolean isPrincipal;

    private Boolean isCpc;

    private Integer postClassId;

    private Integer unitId;

    private Boolean isMainPost;

    private Boolean isFirstMainPost;

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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName == null ? null : postName.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getLpDispatchId() {
        return lpDispatchId;
    }

    public void setLpDispatchId(Integer lpDispatchId) {
        this.lpDispatchId = lpDispatchId;
    }

    public Date getLpWorkTime() {
        return lpWorkTime;
    }

    public void setLpWorkTime(Date lpWorkTime) {
        this.lpWorkTime = lpWorkTime;
    }

    public Integer getNpDispatchId() {
        return npDispatchId;
    }

    public void setNpDispatchId(Integer npDispatchId) {
        this.npDispatchId = npDispatchId;
    }

    public Date getNpWorkTime() {
        return npWorkTime;
    }

    public void setNpWorkTime(Date npWorkTime) {
        this.npWorkTime = npWorkTime;
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

    public Boolean getIsPrincipal() {
        return isPrincipal;
    }

    public void setIsPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
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

    public Boolean getIsMainPost() {
        return isMainPost;
    }

    public void setIsMainPost(Boolean isMainPost) {
        this.isMainPost = isMainPost;
    }

    public Boolean getIsFirstMainPost() {
        return isFirstMainPost;
    }

    public void setIsFirstMainPost(Boolean isFirstMainPost) {
        this.isFirstMainPost = isFirstMainPost;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}