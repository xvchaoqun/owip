package domain.dispatch;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DispatchCadreView implements Serializable {

    public Dispatch getDispatch(){
        return CmTag.getDispatch(dispatchId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }
    public SysUserView getUser(){
        CadreView cadre = getCadre();
        if(cadre==null) return null;
        return CmTag.getUserById(cadre.getUserId());
    }
    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }
    private Integer id;

    private Integer dispatchId;

    private Integer cadreId;

    private Byte type;

    private Integer unitPostId;

    private Integer cadreTypeId;

    private Integer wayId;

    private Integer procedureId;

    private String post;

    private Integer postType;

    private Integer adminLevel;

    private Integer unitId;

    private String remark;

    private Integer sortOrder;

    private String postTeam;

    private String category;

    private Integer year;

    private Date pubTime;

    private Date workTime;

    private Integer dispatchTypeId;

    private String code;

    private Boolean hasChecked;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public Integer getCadreTypeId() {
        return cadreTypeId;
    }

    public void setCadreTypeId(Integer cadreTypeId) {
        this.cadreTypeId = cadreTypeId;
    }

    public Integer getWayId() {
        return wayId;
    }

    public void setWayId(Integer wayId) {
        this.wayId = wayId;
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
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

    public String getPostTeam() {
        return postTeam;
    }

    public void setPostTeam(String postTeam) {
        this.postTeam = postTeam == null ? null : postTeam.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public Integer getDispatchTypeId() {
        return dispatchTypeId;
    }

    public void setDispatchTypeId(Integer dispatchTypeId) {
        this.dispatchTypeId = dispatchTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Boolean getHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(Boolean hasChecked) {
        this.hasChecked = hasChecked;
    }
}