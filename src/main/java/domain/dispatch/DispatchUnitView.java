package domain.dispatch;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DispatchUnitView implements Serializable {
    
    public Dispatch getDispatch(){
        return CmTag.getDispatch(dispatchId);
    }
    
    private Integer id;

    private Integer dispatchId;

    private Integer type;

    private Integer unitId;

    private Integer oldUnitId;

    private String remark;

    private String category;

    private Integer year;

    private Date pubTime;

    private Integer dispatchTypeId;

    private Integer code;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getOldUnitId() {
        return oldUnitId;
    }

    public void setOldUnitId(Integer oldUnitId) {
        this.oldUnitId = oldUnitId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Integer getDispatchTypeId() {
        return dispatchTypeId;
    }

    public void setDispatchTypeId(Integer dispatchTypeId) {
        this.dispatchTypeId = dispatchTypeId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(Boolean hasChecked) {
        this.hasChecked = hasChecked;
    }
}