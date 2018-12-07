package domain.unit;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class UnitTeam implements Serializable {
    
    public Dispatch getAppoint(){
        if(appointDispatchCadreId==null) return null;
        DispatchCadre dispatchCadre = CmTag.getDispatchCadre(appointDispatchCadreId);
        return dispatchCadre==null?null:dispatchCadre.getDispatch();
    }
    public Dispatch getDepose(){
        if(deposeDispatchCadreId==null) return null;
        DispatchCadre dispatchCadre = CmTag.getDispatchCadre(deposeDispatchCadreId);
        return dispatchCadre==null?null:dispatchCadre.getDispatch();
    }
    private Integer id;

    private Integer unitId;

    private Integer year;

    private Boolean isPresent;

    private String name;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date expectDeposeDate;

    private Integer appointDispatchCadreId;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date appointDate;

    private Integer deposeDispatchCadreId;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date deposeDate;

    private String remark;

    private Integer sortOrder;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getExpectDeposeDate() {
        return expectDeposeDate;
    }

    public void setExpectDeposeDate(Date expectDeposeDate) {
        this.expectDeposeDate = expectDeposeDate;
    }

    public Integer getAppointDispatchCadreId() {
        return appointDispatchCadreId;
    }

    public void setAppointDispatchCadreId(Integer appointDispatchCadreId) {
        this.appointDispatchCadreId = appointDispatchCadreId;
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }

    public Integer getDeposeDispatchCadreId() {
        return deposeDispatchCadreId;
    }

    public void setDeposeDispatchCadreId(Integer deposeDispatchCadreId) {
        this.deposeDispatchCadreId = deposeDispatchCadreId;
    }

    public Date getDeposeDate() {
        return deposeDate;
    }

    public void setDeposeDate(Date deposeDate) {
        this.deposeDate = deposeDate;
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