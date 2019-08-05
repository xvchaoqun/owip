package domain.unit;

import java.io.Serializable;
import java.util.Date;

public class UnitPost implements Serializable {
    private Integer id;

    private Integer unitId;

    private String code;

    private String name;

    private String job;

    private Boolean isPrincipal;

    private Byte leaderType;

    private Integer adminLevel;

    private Integer postType;

    private Integer postClass;

    private Boolean isCpc;

    private Byte status;

    private Date abolishDate;

    private Date openDate;

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

    public Boolean getIsPrincipal() {
        return isPrincipal;
    }

    public void setIsPrincipal(Boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public Byte getLeaderType() {
        return leaderType;
    }

    public void setLeaderType(Byte leaderType) {
        this.leaderType = leaderType;
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

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
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