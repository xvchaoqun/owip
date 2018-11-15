package domain.unit;

import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import persistence.cadre.CadrePostMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class UnitPostView implements Serializable {

    public CadrePost cadrePost;

    // 获取当前任职干部
    public CadreView getCadre(){

        if(cadreId==null) return null;

        return CmTag.getCadreById(cadreId);
    }

    public CadrePost getCadrePost(){

        if(cadrePost==null) {
            CadrePostMapper cadrePostMapper = CmTag.getBean(CadrePostMapper.class);
            cadrePost = cadrePostMapper.selectByPrimaryKey(cadrePostId);
        }

        return cadrePost;
    }

    private Integer id;

    private Integer unitId;

    private String code;

    private String name;

    private String job;

    private Boolean isPrincipalPost;

    private Integer adminLevel;

    private Integer postType;

    private Integer postClass;

    private Boolean isCpc;

    private Byte status;

    private Date abolishDate;

    private Integer sortOrder;

    private String remark;

    private String unitName;

    private String unitCode;

    private Integer unitTypeId;

    private Byte unitStatus;

    private Integer unitSortOrder;

    private Integer cadreId;

    private Integer cadreTypeId;

    private Integer cadrePostYear;

    private Integer adminLevelYear;

    private Integer cadrePostId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Boolean getIsPrincipalPost() {
        return isPrincipalPost;
    }

    public void setIsPrincipalPost(Boolean isPrincipalPost) {
        this.isPrincipalPost = isPrincipalPost;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Integer getPostClass() {
        return postClass;
    }

    public void setPostClass(Integer postClass) {
        this.postClass = postClass;
    }

    public Boolean getIsCpc() {
        return isCpc;
    }

    public void setIsCpc(Boolean isCpc) {
        this.isCpc = isCpc;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getAbolishDate() {
        return abolishDate;
    }

    public void setAbolishDate(Date abolishDate) {
        this.abolishDate = abolishDate;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode == null ? null : unitCode.trim();
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Byte getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(Byte unitStatus) {
        this.unitStatus = unitStatus;
    }

    public Integer getUnitSortOrder() {
        return unitSortOrder;
    }

    public void setUnitSortOrder(Integer unitSortOrder) {
        this.unitSortOrder = unitSortOrder;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getCadreTypeId() {
        return cadreTypeId;
    }

    public void setCadreTypeId(Integer cadreTypeId) {
        this.cadreTypeId = cadreTypeId;
    }

    public Integer getCadrePostYear() {
        return cadrePostYear;
    }

    public void setCadrePostYear(Integer cadrePostYear) {
        this.cadrePostYear = cadrePostYear;
    }

    public Integer getAdminLevelYear() {
        return adminLevelYear;
    }

    public void setAdminLevelYear(Integer adminLevelYear) {
        this.adminLevelYear = adminLevelYear;
    }

    public Integer getCadrePostId() {
        return cadrePostId;
    }

    public void setCadrePostId(Integer cadrePostId) {
        this.cadrePostId = cadrePostId;
    }
}