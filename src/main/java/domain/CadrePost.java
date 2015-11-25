package domain;

import java.io.Serializable;
import java.util.Date;

public class CadrePost implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer adminLevelId;

    private Boolean isPresent;

    private Date startTime;

    private Integer startDispatchCadreId;

    private Date endTime;

    private Integer endDispatchCadreId;

    private String remark;

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

    public Integer getAdminLevelId() {
        return adminLevelId;
    }

    public void setAdminLevelId(Integer adminLevelId) {
        this.adminLevelId = adminLevelId;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStartDispatchCadreId() {
        return startDispatchCadreId;
    }

    public void setStartDispatchCadreId(Integer startDispatchCadreId) {
        this.startDispatchCadreId = startDispatchCadreId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndDispatchCadreId() {
        return endDispatchCadreId;
    }

    public void setEndDispatchCadreId(Integer endDispatchCadreId) {
        this.endDispatchCadreId = endDispatchCadreId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}