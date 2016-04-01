package bean;

/**
 * Created by fafa on 2016/3/31.
 */
public class ApplySelfSearchBean {

    private Integer cadreId;
    private Byte type;
    private String applyDateStart;
    private String applyDateEnd;

    public ApplySelfSearchBean(Integer cadreId, Byte type, String applyDateStart, String applyDateEnd) {
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

    public String getApplyDateStart() {
        return applyDateStart;
    }

    public void setApplyDateStart(String applyDateStart) {
        this.applyDateStart = applyDateStart;
    }

    public String getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(String applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }
}
