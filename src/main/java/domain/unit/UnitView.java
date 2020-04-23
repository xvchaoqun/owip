package domain.unit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UnitView implements Serializable {
    private Integer id;

    private String code;

    private String name;

    private Integer typeId;

    private Integer dispatchUnitId;

    private Date workTime;

    private String url;

    private Date createTime;

    private Integer sortOrder;

    private Byte status;

    private Boolean notStatPost;

    private BigDecimal mainPostCount;

    private BigDecimal vicePostCount;

    private BigDecimal nonePostCount;

    private BigDecimal mainKjPostCount;

    private BigDecimal viceKjPostCount;

    private BigDecimal mainCount;

    private BigDecimal viceCount;

    private BigDecimal mainKjCount;

    private BigDecimal viceKjCount;

    private BigDecimal noneCount;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getDispatchUnitId() {
        return dispatchUnitId;
    }

    public void setDispatchUnitId(Integer dispatchUnitId) {
        this.dispatchUnitId = dispatchUnitId;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Boolean getNotStatPost() {
        return notStatPost;
    }

    public void setNotStatPost(Boolean notStatPost) {
        this.notStatPost = notStatPost;
    }

    public BigDecimal getMainPostCount() {
        return mainPostCount;
    }

    public void setMainPostCount(BigDecimal mainPostCount) {
        this.mainPostCount = mainPostCount;
    }

    public BigDecimal getVicePostCount() {
        return vicePostCount;
    }

    public void setVicePostCount(BigDecimal vicePostCount) {
        this.vicePostCount = vicePostCount;
    }

    public BigDecimal getNonePostCount() {
        return nonePostCount;
    }

    public void setNonePostCount(BigDecimal nonePostCount) {
        this.nonePostCount = nonePostCount;
    }

    public BigDecimal getMainKjPostCount() {
        return mainKjPostCount;
    }

    public void setMainKjPostCount(BigDecimal mainKjPostCount) {
        this.mainKjPostCount = mainKjPostCount;
    }

    public BigDecimal getViceKjPostCount() {
        return viceKjPostCount;
    }

    public void setViceKjPostCount(BigDecimal viceKjPostCount) {
        this.viceKjPostCount = viceKjPostCount;
    }

    public BigDecimal getMainCount() {
        return mainCount;
    }

    public void setMainCount(BigDecimal mainCount) {
        this.mainCount = mainCount;
    }

    public BigDecimal getViceCount() {
        return viceCount;
    }

    public void setViceCount(BigDecimal viceCount) {
        this.viceCount = viceCount;
    }

    public BigDecimal getMainKjCount() {
        return mainKjCount;
    }

    public void setMainKjCount(BigDecimal mainKjCount) {
        this.mainKjCount = mainKjCount;
    }

    public BigDecimal getViceKjCount() {
        return viceKjCount;
    }

    public void setViceKjCount(BigDecimal viceKjCount) {
        this.viceKjCount = viceKjCount;
    }

    public BigDecimal getNoneCount() {
        return noneCount;
    }

    public void setNoneCount(BigDecimal noneCount) {
        this.noneCount = noneCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}