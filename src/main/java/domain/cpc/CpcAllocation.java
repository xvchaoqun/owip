package domain.cpc;

import java.io.Serializable;

public class CpcAllocation implements Serializable {
    private Integer id;

    private Integer unitId;

    private Integer adminLevelId;

    private Integer num;

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

    public Integer getAdminLevelId() {
        return adminLevelId;
    }

    public void setAdminLevelId(Integer adminLevelId) {
        this.adminLevelId = adminLevelId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}