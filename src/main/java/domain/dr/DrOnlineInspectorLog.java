package domain.dr;

import persistence.dr.DrOnlineInspectorTypeMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DrOnlineInspectorLog implements Serializable {
    public DrOnlineInspectorType getInspectorType(){
        DrOnlineInspectorTypeMapper inspectorTypeMapper = CmTag.getBean(DrOnlineInspectorTypeMapper.class);
        return inspectorTypeMapper.selectByPrimaryKey(typeId);
    }
    private Integer id;

    private Integer onlineId;

    private Integer typeId;

    private Integer unitId;

    private String remark;

    private Integer totalCount;

    private Integer finishCount;

    private Integer pubCount;

    private Date createTime;

    private Integer exportCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getPubCount() {
        return pubCount;
    }

    public void setPubCount(Integer pubCount) {
        this.pubCount = pubCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExportCount() {
        return exportCount;
    }

    public void setExportCount(Integer exportCount) {
        this.exportCount = exportCount;
    }
}