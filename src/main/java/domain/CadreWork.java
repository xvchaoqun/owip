package domain;

import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CadreWork implements Serializable {

    public List<DispatchCadreRelate> getDispatchCadreRelates(){
        return CmTag.findDispatchCadreRelates(id, SystemConstants.DISPATCH_CADRE_RELATE_TYPE_WORK);
    }

    private Integer id;

    private Integer fid;

    private Integer subWorkCount;

    private Integer cadreId;

    private Date startTime;

    private Date endTime;

    private String unit;

    private String post;

    private Integer typeId;

    private Integer workType;

    private Boolean isCadre;

    private String remark;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
}