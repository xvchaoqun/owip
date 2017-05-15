package bean;

import java.util.Date;

/**
 * Created by fafa on 2016/3/31.
 */
public class ApplySelfSearchBean {

    private Integer cadreId;
    private Byte type;
    private Date applyDateStart;
    private Date applyDateEnd;

    public ApplySelfSearchBean(Integer cadreId, Byte type, Date applyDateStart, Date applyDateEnd) {
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
