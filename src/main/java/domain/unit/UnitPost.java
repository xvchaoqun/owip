package domain.unit;

import java.io.Serializable;
import java.util.Date;

public class UnitPost implements Serializable {
    private Integer id;

    private Integer unitId;

    private Integer code;

    private String name;

    private String job;

    private Boolean isPrincipalPost;

    private Integer adminLevel;

    private Integer postType;

    private Integer postClass;

    private Boolean isCpc;

    private Byte status;

    private Date abolishTime;

    private Integer sortOrder;

    private String remark;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public Date getAbolishTime() {
        return abolishTime;
    }

    public void setAbolishTime(Date abolishTime) {
        this.abolishTime = abolishTime;
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