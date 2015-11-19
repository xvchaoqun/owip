package domain;

import java.io.Serializable;

public class DispatchUnitRelate implements Serializable {
    private Integer id;

    private Integer dispatchUnitId;

    private Integer unitId;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDispatchUnitId() {
        return dispatchUnitId;
    }

    public void setDispatchUnitId(Integer dispatchUnitId) {
        this.dispatchUnitId = dispatchUnitId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}