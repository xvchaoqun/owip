package domain.dispatch;

import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;

public class DispatchUnit implements Serializable {

    public Dispatch getDispatch(){
        return CmTag.getDispatch(dispatchId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }

    private Integer id;

    private Integer dispatchId;

    private Integer unitId;

    private Integer typeId;

    private Integer year;

    private String remark;

    private Integer sortOrder;

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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
}