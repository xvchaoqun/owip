package domain.cadre;

import domain.dispatch.DispatchCadreRelate;
import sys.constants.DispatchConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CadreWork implements Serializable {
    public List<DispatchCadreRelate> getDispatchCadreRelates(){
        return CmTag.findDispatchCadreRelates(id, DispatchConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
    }
    private List<CadreWork> subCadreWorks;

    public List<CadreWork> getSubCadreWorks() {
        return subCadreWorks;
    }

    public void setSubCadreWorks(List<CadreWork> subCadreWorks) {
        this.subCadreWorks = subCadreWorks;
    }

    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    private Integer id;

    private Integer fid;

    private Boolean isEduWork;

    private Integer subWorkCount;

    private Integer cadreId;

    private Date startTime;

    private Date endTime;

    private String detail;

    private String unitIds;

    private Integer workType;

    private Boolean isCadre;

    private String remark;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Boolean getIsEduWork() {
        return isEduWork;
    }

    public void setIsEduWork(Boolean isEduWork) {
        this.isEduWork = isEduWork;
    }

    public Integer getSubWorkCount() {
        return subWorkCount;
    }

    public void setSubWorkCount(Integer subWorkCount) {
        this.subWorkCount = subWorkCount;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds == null ? null : unitIds.trim();
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean isCadre) {
        this.isCadre = isCadre;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}