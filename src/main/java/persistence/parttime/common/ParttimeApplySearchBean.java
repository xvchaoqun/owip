package persistence.parttime.common;

import java.util.Date;

public class ParttimeApplySearchBean {

    private Integer cadreId;
    private Byte type;
    private Date applyDateStart;
    private Date applyDateEnd;

    public ParttimeApplySearchBean(Integer cadreId, Byte type, Date applyDateStart, Date applyDateEnd) {
        this.cadreId = cadreId;
        this.type = type;
        this.applyDateStart = applyDateStart;
        this.applyDateEnd = applyDateEnd;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getApplyDateStart() {
        return applyDateStart;
    }

    public void setApplyDateStart(Date applyDateStart) {
        this.applyDateStart = applyDateStart;
    }

    public Date getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(Date applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }
}
