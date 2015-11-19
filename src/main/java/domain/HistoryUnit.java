package domain;

import java.io.Serializable;
import java.util.Date;

public class HistoryUnit implements Serializable {
    private Integer id;

    private Integer unitId;

    private Integer oldUnitId;

    private Integer sortOrder;

    private Date createTime;

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

    public Integer getOldUnitId() {
        return oldUnitId;
    }

    public void setOldUnitId(Integer oldUnitId) {
        this.oldUnitId = oldUnitId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}